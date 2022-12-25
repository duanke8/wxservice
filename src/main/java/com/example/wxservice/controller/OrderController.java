package com.example.wxservice.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wxservice.dto.OrderDto;
import com.example.wxservice.service.OrderService;
import com.example.wxservice.util.R;
import com.example.wxservice.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(orderDetial, orderVo);
        orderVo.setCreateTime(DateUtil.formatDateTime(orderDetial.getCreateTime()));
        orderVo.setUpdateTime(DateUtil.formatDateTime(orderDetial.getUpdateTime()));
        return R.ok(orderVo);
    }

    @GetMapping("/getOrderList")
    public R getOrderList() {
        IPage<OrderDto> page = orderService.page(new Page());
        List<OrderVo> orderVoList = page.getRecords().stream().map(orderDto -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(orderDto, orderVo);
            orderVo.setCreateTime(DateUtil.formatDateTime(orderDto.getCreateTime()));
            orderVo.setUpdateTime(DateUtil.formatDateTime(orderDto.getUpdateTime()));
            orderVo.setTrackingNumber(Strings.isBlank(orderVo.getTrackingNumber()) ? "-" : orderVo.getTrackingNumber());
            return orderVo;
        }).collect(Collectors.toList());
        Page<OrderVo> newPage = new Page<>();
        newPage.setRecords(orderVoList);
        return R.ok(newPage);
    }


}
