package com.hao.eduservice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：EduApplication
 * @description ：TODO
 * @create 2020-12-03-21:14
 */
@SpringBootApplication
@EnableFeignClients //启用feign
@EnableDiscoveryClient //启用nacos
@ComponentScan(basePackages = {"com.hao"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
