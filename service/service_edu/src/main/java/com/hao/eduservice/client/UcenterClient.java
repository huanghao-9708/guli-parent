package com.hao.eduservice.client;

import com.hao.utils.R;
import com.hao.utils.orderVo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：UcenterClient
 * @description ：TODO
 * @create 2021-01-14-12:41
 */
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {
    @GetMapping("/ucenter/member/getMemberInfo/{id}")
    public UcenterMemberOrder getMemberInfoById(@PathVariable("id")String id);
}
