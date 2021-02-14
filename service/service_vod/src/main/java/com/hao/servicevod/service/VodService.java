package com.hao.servicevod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：VodService
 * @description ：TODO
 * @create 2021-01-03-13:15
 */
public interface VodService {
    String uploadAliyunVideo(MultipartFile file);

    boolean deleteAliyunVideoById(String videoId);
    boolean deleteAliyunVideoBatch(List<String> ids);

    String getPlayAuth(String id);
}
