package com.hao.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：CrmApplication
 * @description ：TODO
 * @create 2021-01-05-12:28
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.hao"})
//@EnableDiscoveryClient //启用nacos
//@MapperScan("com.hao.educms.mapper")
public class CrmApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class);
    }
}
