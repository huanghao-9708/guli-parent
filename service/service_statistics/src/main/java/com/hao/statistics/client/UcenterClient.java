package com.hao.statistics.client;

import com.hao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @title: ucenterClient
 * @Author hao
 * @Date: 2021/1/28 15:19
 * @email: 974721694@qq.com
 * @Version 1.0
 */
@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {
    @GetMapping("/ucenter/member/getCountRegister/{day}")
    R getCountRegister(@PathVariable("day") String day);
}
