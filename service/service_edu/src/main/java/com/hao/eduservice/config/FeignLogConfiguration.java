package com.hao.eduservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：FeginLogConfig
 * @description ：TODO
 * @create 2021-01-04-13:07  http://localhost:8003/eduvod/video/deleteAliyunVideo/555'
 */
@Configuration
public class FeignLogConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {

        return Logger.Level.FULL;

    }
}
