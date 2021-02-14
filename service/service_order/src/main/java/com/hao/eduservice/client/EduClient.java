package com.hao.eduservice.client;

import com.hao.utils.orderVo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-edu",fallback = EduClientImpl.class)
@Component
public interface EduClient {
    //    为其他服务进行课程查询
    @GetMapping("/eduservice/course/getCourse/{id}")
    CourseWebVoOrder getCourse(@PathVariable("id") String id);
}
