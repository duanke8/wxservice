package com.example.wxservice.controller;

import com.example.wxservice.dto.FileDto;
import com.example.wxservice.service.FileService;
import com.example.wxservice.test.ftpfileload.SFTPUtil;
import com.example.wxservice.util.R;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;
    @Resource
    private SFTPUtil sftpUtil;

    // 文件上传后的路径
    String uploadSftpPath = "/test/file/temp/";

    @PostMapping("/getFileList")
    public R getFileList() {
        List<FileDto> list = fileService.list();
        return R.ok(list);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(String id) throws IOException {
        FileDto fileDto = fileService.getById(id);
        File file = new File(fileDto.getFilePath());
        byte[] data = Files.readAllBytes(file.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());
        headers.setContentLength(data.length);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @GetMapping("/deleteFile")
    public R deleteFile(String id) throws IOException {
        FileDto fileDto = fileService.getById(id);
        File file = new File(fileDto.getFilePath());
        file.delete();
        fileService.removeById(id);
        return R.ok("删除成功");
    }


    @PostMapping("/upload")
    public R uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("文件为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();// 文件上传后的路径
        log.info("uploadFile fileName:{}", fileName);
        // 文件上传后的路径
        String filePath = null;
        try {
            filePath = new File("").getCanonicalPath() + "/tmp/uploadFile/";
        } catch (IOException e) {
            e.printStackTrace();
        }
        //存储路径
        String tagFilePath = filePath + System.currentTimeMillis() + fileName;
        log.info("tagFilePath:{}", tagFilePath);
        File dest = new File(tagFilePath);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            log.info("uploadFile file.transferTo:{}");
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(fileName + "上传失败");
        }

        FileDto fileDto = insertFileRecordIntoDB(fileName, tagFilePath);
        return R.ok(fileDto);
    }

    @PostMapping("/uploadSftp")
    public R uploadSftp(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("文件为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();// 文件上传后的路径
        log.info("uploadFile fileName:{}", fileName);

        //存储路径
        String tagFilePath = uploadSftpPath + fileName;
        log.info("tagFilePath:{}", tagFilePath);
        File dest = new File(tagFilePath);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            log.info("uploadFile file.transferTo:{}");
            sftpUtil.upload(tagFilePath, fileName, file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(fileName + "上传失败");
        }

        FileDto fileDto = insertFileRecordIntoDB(fileName, tagFilePath);
        return R.ok(fileDto);
    }

    @GetMapping("/downloadSftp")
    public ResponseEntity<byte[]> downloadSftp(String id) throws IOException, SftpException {
        FileDto fileDto = fileService.getById(id);
        sftpUtil.login();
        byte[] fileData = sftpUtil.download(uploadSftpPath,fileDto.getFileName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileDto.getFileName());
        headers.setContentLength(fileData.length);
        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    private FileDto insertFileRecordIntoDB(String fileName, String tagFilePath) {
        FileDto fileDto = new FileDto();
        fileDto.setFilePath(tagFilePath);
        fileDto.setFileName(fileName);
        fileDto.setUrl(tagFilePath);
        fileDto.setName(fileName);
        fileService.save(fileDto);
        return fileDto;
    }
}