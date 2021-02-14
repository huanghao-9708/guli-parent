package com.hao.eduservice.controller;


import com.hao.eduservice.entity.Order;
import com.hao.eduservice.service.OrderService;
import com.hao.utils.JwtUtils;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/eduorder/order")

public class OrderController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

//    生成订单借口
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderNo",orderNo);
    }
//    根据订单id查询订单信息
    @GetMapping("getOrder/{orderNo}")
    public R getOrderByNo(@PathVariable String orderNo){
        Order order = orderService.getOrderByNo(orderNo);
        return R.ok().data("order",order);
    }
//    根据用户id和课程id来查看用户是否购买了该课程
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        return orderService.isBuyCourse(courseId,memberId);
    }
}

