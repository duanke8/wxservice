package com.example.wxservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wxservice.dto.FileDto;
import com.example.wxservice.mapper.FileMapper;
import com.example.wxservice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDto> implements FileService {
}
