package com.hao.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：OssService
 * @description ：TODO
 * @create 2020-12-11-19:42
 */
public interface OssService {
//    上传头像到oss中
    String uploadFileAvatar(MultipartFile file);
}
