package com.hao.eduservice.controller;


import com.hao.eduservice.service.PayLogService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/eduorder/paylog")

public class PayLogController {
    private PayLogService payLogService;
    @Autowired
    public void setPayLogService(PayLogService payLogService) {
        this.payLogService = payLogService;
    }
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
//        返回二维码地址，还有其他的一些信息
        Map<String,Object> data = payLogService.createNative(orderNo);
        return R.ok().data("data",data);
    }
//    查询订单支付的状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> data = payLogService.queryPayStatus(orderNo);
        if (data == null){
            return R.error().msg("支付出错");
        }
        if ("SUCCESS".equals(data.get("trade_state"))){
//            添加记录到支付表立，更新订单表的状态
            payLogService.updateOrdersStatus(data);
            return R.ok().msg("支付成功");
        }
        return R.ok().code(25000);
    }
}

