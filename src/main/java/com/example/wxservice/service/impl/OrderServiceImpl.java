package com.example.wxservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wxservice.dto.OrderDto;
import com.example.wxservice.mapper.OrderMapper;
import com.example.wxservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDto> implements OrderService   {
}
