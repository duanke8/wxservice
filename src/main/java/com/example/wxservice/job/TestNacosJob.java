package com.example.wxservice.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class TestNacosJob {
//    @Value("${test.config2}")
//    String config;
//    @Value("${test.test}")
//    String testtest;
//
//    @Scheduled(cron = "0/1 * * * * ?")
//    public void test() {
//        log.info(config);
//        log.info(testtest);
//        log.info("*************");
//
//    }
}
