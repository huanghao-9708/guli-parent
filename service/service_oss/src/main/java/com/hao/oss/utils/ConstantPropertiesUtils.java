package com.hao.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：ConstantPropertiesUtils
 * @description ：读取application里面的 阿里oss的一些配置
 * @create 2020-12-11-19:32
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {
//    读取阿里云的配置信息
    @Value("${aliyun.oss.file.endPoint}")
    private String endPoint;
    @Value("${aliyun.oss.file.keyId}")
    private String keyId;
    @Value("${aliyun.oss.file.keySecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketName}")
    private String bucketName;

//    向外暴露静态常量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endPoint;
        KEY_SECRET= keySecret;
        KEY_ID = keyId;
        BUCKET_NAME = bucketName;
    }
}
