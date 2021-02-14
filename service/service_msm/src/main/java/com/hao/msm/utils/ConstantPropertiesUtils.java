package com.hao.msm.utils;

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
    @Value("${aliyun.msm.file.keyId}")
    private String keyId;
    @Value("${aliyun.msm.file.keySecret}")
    private String keySecret;
    @Value("${aliyun.msm.file.signName}")
    private String signName;
    @Value("${aliyun.msm.file.templateCode}")
    private String templateCode;

//    向外暴露静态常量
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String SIGN_NAME;
    public static String TEMPLATE_CODE;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_SECRET= keySecret;
        KEY_ID = keyId;
        SIGN_NAME = signName;
        TEMPLATE_CODE = templateCode;
    }
}
