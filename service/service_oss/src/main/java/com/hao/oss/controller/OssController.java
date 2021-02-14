package com.hao.oss.controller;

import com.hao.oss.service.OssService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：OssController
 * @description ：TODO
 * @create 2020-12-11-19:42
 */
@RequestMapping("eduoss/fileoss")
@RestController

public class OssController {
    @Autowired
    private OssService ossService;
    //    上传头像方法
    @PostMapping
    public R uploadFile(MultipartFile file){
//        获取上传文件
//        返回上传到oss里
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
