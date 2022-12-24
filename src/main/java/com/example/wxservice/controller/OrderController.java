package com.example.wxservice.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wxservice.dto.OrderDto;
import com.example.wxservice.service.OrderService;
import com.example.wxservice.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrderDetial")
    public R getOrderDetial(@RequestParam("orderId") String orderId) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        Wrapper queryWrapper = new QueryWrapper(orderDto);
        OrderDto orderDetial = orderService.getOne(queryWrapper);
        return R.ok(orderDetial);
    }

    @GetMapping("/getOrderList")
    public R getOrderList() {
        IPage<OrderDto> page = orderService.page(new Page());
        return R.ok(page);
    }


}
