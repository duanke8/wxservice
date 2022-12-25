package com.example.wxservice.vo;

import lombok.Data;

/**
 * @program: wxservice
 * @description:
 * @author: 段珂 dwx922021
 * @create: 2021-11-20 21:34
 **/
@Data
public class Token {
    private String access_token;
    private String expires_in;
}
