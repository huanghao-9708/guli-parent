package com.hao.canal;

import com.hao.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @title: CanalApplication
 * @Author hao
 * @Date: 2021/2/9 18:21
 * @email: 974721694@qq.com
 * @Version 1.0
 */
@SpringBootApplication
public class CanalApplication implements CommandLineRunner {
    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class);
    }
    @Override
    public void run(String... args) throws Exception {
//        项目启动的时候对canal客户端进行监听
        canalClient.run();
    }
}
