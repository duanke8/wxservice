package com.example.wxservice.entity;

import lombok.Data;

@Data
public class RequestVo {
    /**
     * signature = data.signature
     * timestamp = data.timestamp
     * nonce = data.nonce
     * echostr = data.echostr
     *
     * @param request
     * @param response
     * @return
     */
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
