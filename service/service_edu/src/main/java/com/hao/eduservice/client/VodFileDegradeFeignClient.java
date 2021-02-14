package com.hao.eduservice.client;

import com.hao.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：VodFileDegradeFeignClient
 * @description ：TODO voidClient的熔断实现类
 * @create 2021-01-04-18:06
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R deleteAliyunVideoById(String videoId) {
        return R.error().data("msg","删除视频出错");
    }

    @Override
    public R deleteAliyunVideoByBatch(List<String> ids) {
        return R.error().data("msg","删除视频出错");
    }
}
