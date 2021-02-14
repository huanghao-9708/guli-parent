package com.hao.ucenter.utils;

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
    @Value("${wx.open.app_id}")
    private String appId;
    @Value("${wx.open.app_secret}")
    private String appSecret;
    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

//    向外暴露静态常量
    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appId;
        APP_SECRET= appSecret;
        REDIRECT_URL = redirectUrl;
    }
}
