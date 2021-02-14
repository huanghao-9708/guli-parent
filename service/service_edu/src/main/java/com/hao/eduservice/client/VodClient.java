package com.hao.eduservice.client;

import com.hao.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：VodClient
 * @description ：TODO
 * @create 2021-01-04-10:47
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    @DeleteMapping("/eduvod/video/deleteAliyunVideo/{videoId}")
    R deleteAliyunVideoById(@PathVariable("videoId") String videoId);
    @DeleteMapping("/eduvod/video/deleteAliyunVideoBatch")
    R deleteAliyunVideoByBatch(@RequestBody List<String> ids);
}