package com.example.wxservice.test.ftpfileload;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * 类说明 sftp工具类
 */
@Component
@Slf4j
public class SFTPUtil {
    private ChannelSftp sftp;

    private Session session;
    /**
     * SFTP 登录用户名
     */
    private String username;
    /**
     * SFTP 登录密码
     */
    private String password;
    /**
     * SFTP 服务器地址IP地址
     */
    private String host;
    /**
     * SFTP 端口
     */
    private int port;
    /**
     * 私钥
     */
    private String privateKey;

    @PostConstruct
    private void init() {
        this.username = "root";
        this.password = "rx1314520.";
        this.host = "60.205.209.65";
        this.port = 22;
        login();
    }

    /**
     * 构造基于密码认证的sftp对象
     */
    public SFTPUtil(String username, String password, String host, int port) {
        this.username = "root";
        this.password = "rx1314520.";
        this.host = "60.205.209.65";
        this.port = 22;
    }

    /**
     * 构造基于秘钥认证的sftp对象
     */
    public SFTPUtil(String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    public SFTPUtil() {
    }


    /**
     * 连接sftp服务器
     */
    public void login() {
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥  
            }

            session = jsch.getSession(username, host, port);

            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }


    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     *
     * @param basePath     服务器的基础路径
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     */
    public void upload(String basePath, String directory, String sftpFileName, InputStream input) throws SftpException {
        try {
            sftp.cd(basePath);
            sftp.cd(directory);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String[] dirs = directory.split("/");
            String tempPath = basePath;
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)) continue;
                tempPath += "/" + dir;
                try {
                    sftp.cd(tempPath);
                } catch (SftpException ex) {
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
        }
        sftp.put(input, sftpFileName);  //上传文件
    }

    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     *
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     */
    public void upload(String directory, String sftpFileName, InputStream input) throws SftpException {
        upload(directory, "", sftpFileName, input);
    }

    /**
     * 下载文件。
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        String pathname = saveFile + downloadFile;
        File file = new File(pathname);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd("/");
            sftp.cd(directory);
//            sftp.lcd(directory);
        }
        InputStream is = sftp.get(downloadFile);

        byte[] fileData = IOUtils.toByteArray(is);

        return fileData;
    }


    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }


    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    //上传文件测试
    public static void main(String[] args) throws Exception {
//        testUpload();
        testDownload();
    }

    private static void testDownload() throws SftpException, IOException {
        SFTPUtil sftp = new SFTPUtil("root", "rx1314520.", "60.205.209.65", 22);
        sftp.login();
        String uploadSftpPath = "/test/file/temp/";
        System.out.println("***************");
        String filename = "redis.exe";
        String targetPath = "E:\\";

        sftp.download(uploadSftpPath, filename);
        sftp.download(uploadSftpPath, filename,targetPath);
        System.out.println(".......");
    }

    private static void testUpload() throws FileNotFoundException, SftpException {
        long a1 = System.currentTimeMillis();
        SFTPUtil sftp = new SFTPUtil("root", "rx1314520.", "60.205.209.65", 22);
        sftp.login();
        long a2 = System.currentTimeMillis();
        System.out.println("登录耗时：" + (a2 - a1) + ",上传开始....");

        String fileName = "redis.exe";
        File file = new File("E:\\toolsAz\\" + fileName);
        InputStream is = new FileInputStream(file);

        long a3 = System.currentTimeMillis();
        sftp.upload("/test/file/temp", "", fileName, is);
        long a4 = System.currentTimeMillis();
        sftp.logout();

        System.out.println("总耗时：" + (a4 - a1) + "，上传耗时：" + (a4 - a3));
    }

}