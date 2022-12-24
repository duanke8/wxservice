package com.example.wxservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossInterceptor implements WebMvcConfigurer {
    //允许跨域请求
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("......跨域执行......");
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","POST","PUT","OPTIONS")
                .allowCredentials(true)
                .maxAge(5000);
    }
}