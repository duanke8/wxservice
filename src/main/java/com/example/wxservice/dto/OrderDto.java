package com.example.wxservice.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("order_info")
public class OrderDto {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderId;
    private String orderInfo;
    private String orderImages;
    private Date createTime;
    private Date updateTime;
    private String createUserId;


}
