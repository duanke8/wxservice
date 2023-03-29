package com.example.wxservice;

import com.example.wxservice.config.CustomMultipartResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;

/**
 *
 */
@SpringBootApplication
//注意取消自动Multipart配置，否则可能在上传接口中拿不到file的值
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
public class WxserviceApplication {
    //注入自定义的文件上传处理类
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CustomMultipartResolver customMultipartResolver = new CustomMultipartResolver();
        return customMultipartResolver;
    }

    public static void main(String[] args) {
        SpringApplication.run(WxserviceApplication.class, args);
    }

}
