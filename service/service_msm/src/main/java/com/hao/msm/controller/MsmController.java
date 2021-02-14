package com.hao.msm.controller;

import com.hao.msm.service.MsmService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：MsmController
 * @description ：TODO
 * @create 2021-01-06-16:35
 */

@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {
    private MsmService msmService;

    @Autowired
    public void setMsmService(MsmService msmService) {
        this.msmService = msmService;
    }

    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        return msmService.sendMsm(phone)?R.ok():R.error().data("msg","发送信息失败");
    }
}
