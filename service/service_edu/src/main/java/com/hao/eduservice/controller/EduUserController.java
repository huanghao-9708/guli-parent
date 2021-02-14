package com.hao.eduservice.controller;

import com.hao.utils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：EduUserController
 * @description ：TODO
 * @create 2020-12-09-14:12
 */
@RestController
@RequestMapping("/eduservice/user")

public class EduUserController {
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    @GetMapping("info")
    public R getInfo(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","http://www.520touxiang.com/uploads/allimg/160914/3-160914161309-52.gif");
    }
}
