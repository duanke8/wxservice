package com.example.wxservice.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("icc_info")
public class IccDto {
    /**
     *     `id` INT (11) NOT NULL AUTO_INCREMENT,
     *     `function` VARCHAR (100) DEFAULT NULL COMMENT '功能',
     *     `command` VARCHAR (400) DEFAULT NULL COMMENT '命令',
     *     `remarks` VARCHAR (100) DEFAULT NULL COMMENT '备注',
     *     `type` VARCHAR (10) DEFAULT NULL COMMENT 'icc/8900',
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("functionStr")
    private String functionStr;
    private String command;
    private String remarks;
    private String type;
}
