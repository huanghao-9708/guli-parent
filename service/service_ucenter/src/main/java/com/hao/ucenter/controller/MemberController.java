package com.hao.ucenter.controller;


import com.hao.ucenter.entity.Member;
import com.hao.ucenter.entity.vo.RegisterVO;
import com.hao.ucenter.service.MemberService;
import com.hao.utils.JwtUtils;
import com.hao.utils.R;
import com.hao.utils.orderVo.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/ucenter/member")

public class MemberController {
    private MemberService memberService;
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("login")
    public R login(@RequestBody Member member){
        //member 里面封装了phone 和password
        //使用memberService的登录服务，并返回一个token
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }
    @PostMapping("register")
    public R register(@RequestBody RegisterVO registerVO){
        memberService.register(registerVO);
        return R.ok();
    }
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        Member member = memberService.getMemberInfo(request);
        return R.ok().data("member",member);
    }
//    为其他服务进行用户查询
    @GetMapping("getMemberInfo/{id}")
    public UcenterMemberOrder getMemberInfoById(@PathVariable String id){
        return memberService.getUcenterMemberOrderById(id);
    }
//    统计指定日期所有注册的用户数
    @GetMapping("getCountRegister/{day}")
    public R getCountRegister(@PathVariable String day){
        Integer count =  memberService.getCountRegister(day);
        return R.ok().data("count",count);
    }
}

