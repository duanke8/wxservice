package com.example.wxservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wxservice.dto.FileDto;
import com.example.wxservice.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<FileDto> {
}
