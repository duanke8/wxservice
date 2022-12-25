package com.example.wxservice.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
public class OrderVo {

    private Long id;
    private String orderId;
    private String orderInfo;
    private String orderImages;
    private String createTime;
    private String updateTime;
    private String createUserId;
    private String trackingNumber;
    private String trackingDetial;


}
