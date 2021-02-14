package com.hao.ucenter.controller;

import com.google.gson.Gson;
import com.hao.servicebase.config.exceptionHandler.GuliException;
import com.hao.ucenter.entity.Member;
import com.hao.ucenter.service.MemberService;
import com.hao.ucenter.utils.ConstantPropertiesUtils;
import com.hao.ucenter.utils.HttpClientUtils;
import com.hao.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：WxApiController
 * @description ：TODO
 * @create 2021-01-09-14:30
 */
@Controller

@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    private MemberService memberService;
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("callback")
    public String callback(String code,String state){
//        1,构建url，通过httpclient发送请求获取open_id和access_token
        String baseUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String getTokenUrl = String.format(
                baseUrl,
                ConstantPropertiesUtils.APP_ID,
                ConstantPropertiesUtils.APP_SECRET,
                code
        ) ;
            //发送请求的结果
        String result = null;
        try {
            result = HttpClientUtils.get(getTokenUrl, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        2，解析返回数据查看数据库内有无对于open_id的用户，如果有就获取，没有通过open_id再发送请求对应的微信信息
        Member member=null;
        if (!StringUtils.isEmpty(result)){//查看返回值是否为空
            Gson gson = new Gson();
            HashMap tokenMap = gson.fromJson(result, HashMap.class);
            String openId = (String) tokenMap.get("openid");
            String accessToken = (String) tokenMap.get("access_token");
            member = memberService.getMemberByOpenId(openId);
            if (member==null){//如果数据库中没有对应openId的用户
                member = new Member();

//                发送请求通过open_id和access_token获取用户的微信信息并注册
                String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                        "access_token=" +accessToken+
                        "&openid="+openId;
                try {
                    result = HttpClientUtils.get(getUserInfoUrl, "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                HashMap userInfoMap = gson.fromJson(result, HashMap.class);
                member.setOpenid(openId);
                member.setNickname((String) userInfoMap.get("nickname"));
                member.setAvatar((String) userInfoMap.get("headimgurl"));
                memberService.save(member);
            }
        }else {
            throw new GuliException(20001,"获取微信open_id和token失败");
        }
//        3，将用户对象构造成token放入连接中，回到首页
        String userToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        String indexUrl = "http://localhost:3000?token="+userToken;
        return "redirect:"+indexUrl;
    }
    @GetMapping("login")
    public String login(){
        //创建基础的微信开放平台授权链接
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect"+
                "?appid=%s"+
                "&redirect_uri=%s"+
                "&response_type=code"+
                "&scope=snsapi_login"+
                "&state=%s"+
                "#wechat+redirect";
        //获取回调地址并进行编码
        String redirectUrl = ConstantPropertiesUtils.REDIRECT_URL;
        try {
            redirectUrl= URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String state = "guli";
        //将数据填入基础的连接中
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtils.APP_ID,
                redirectUrl,
                state
        );
        //跳转到二维码页面
        return "redirect:"+qrcodeUrl;
    }
}
