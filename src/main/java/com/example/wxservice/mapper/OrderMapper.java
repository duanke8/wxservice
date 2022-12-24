package com.example.wxservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wxservice.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<OrderDto> {
}
