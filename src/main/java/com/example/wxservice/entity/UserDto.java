package com.example.wxservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserDto {

  private long id;
  private String name;
  private String fromUserName;
  private String toUserName;
  private String phoneNumber;


}
