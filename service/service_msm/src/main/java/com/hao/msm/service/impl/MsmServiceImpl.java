package com.hao.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.hao.msm.service.MsmService;
import com.hao.msm.utils.ConstantPropertiesUtils;
import com.hao.msm.utils.RandomUtil;
import com.netflix.client.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：MsmServiceImpl
 * @description ：TODO
 * @create 2021-01-06-16:40
 */
@Service
public class MsmServiceImpl implements MsmService {
    private final Long CODE_TIMEOUT_IN_REDIS = 300L;//验证码在redis里的过期时间，单位是秒
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    public MsmServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //    发送验证码业务
    @Override
    public boolean sendMsm(String phone) {
        //查看phone是否为空
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        //查看redis中是否有该号码的验证码，有则直接返回
        String phoneCodeInRedis = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(phoneCodeInRedis)){
            return false;
        }
        //创建一个随机的6位验证码
        String sixBitRandom = RandomUtil.getSixBitRandom();
        HashMap<String, String> param = new HashMap<>();
        param.put("code",sixBitRandom);
//        发送短信
        redisTemplate.opsForValue().set(phone,sixBitRandom,this.CODE_TIMEOUT_IN_REDIS, TimeUnit.SECONDS);
        System.out.println("msgCode:"+sixBitRandom);
        return true;
    }
//    具体使用阿里云发送短信
    private boolean sendAliyunMsm(String phone, Map<String,String> param){
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
//        发送短信所需要的一些信息
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessSecret = ConstantPropertiesUtils.KEY_SECRET;
        String signName = ConstantPropertiesUtils.SIGN_NAME;
        String templateCode = ConstantPropertiesUtils.TEMPLATE_CODE;

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (com.aliyuncs.exceptions.ServerException e) {
            e.printStackTrace();
        } catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
