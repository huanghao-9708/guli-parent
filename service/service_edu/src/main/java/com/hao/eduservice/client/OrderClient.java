package com.hao.eduservice.client;

import com.hao.utils.orderVo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-order",fallback = OrderClientImpl.class)
@Component
public interface OrderClient {
    //    用户订单中是否已经购买课程
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    Boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId);
}
