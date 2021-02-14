package com.hao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.eduservice.client.EduClient;
import com.hao.eduservice.client.UcenterClient;
import com.hao.eduservice.entity.Order;
import com.hao.eduservice.mapper.OrderMapper;
import com.hao.eduservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.eduservice.utils.OrderNoUtil;
import com.hao.servicebase.config.exceptionHandler.GuliException;
import com.hao.utils.orderVo.CourseWebVoOrder;
import com.hao.utils.orderVo.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-01-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private EduClient eduClient;
    private UcenterClient ucenterClient;
    @Autowired
    public void setEduClient(EduClient eduClient) {
        this.eduClient = eduClient;
    }

    @Autowired
    public void setUcenterClient(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }
    private final Integer WX_PAY_TYPE_CODE=1;
    private final Integer ALI_PAY_TYPE_CODE=2;
    private final Integer SUCCESS_STATUS_CODE=1;
    private final Integer FAILED_STATUS_CODE=0;
    @Override
    public String createOrders(String courseId, String memberId) {
//        先判断课程id 用户id是否为空，如果为空直接抛出异常
        if (StringUtils.isEmpty(courseId)||StringUtils.isEmpty(memberId)){
            throw new GuliException(20001,"创建订单失败请重试，请先登录再试");
        }
//        1，查询出课程信息
        CourseWebVoOrder course = eduClient.getCourse(courseId);
//        2，查询出用户信息
        UcenterMemberOrder memberOrder = ucenterClient.getMemberInfoById(memberId);
//        3，创建订单
        Order order = new Order();
//            创建订单号
        String orderNo = OrderNoUtil.getOrderNo();
        order.setOrderNo(orderNo);
        order.setCourseId(course.getId());
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherName(course.getTeacherName());
        order.setMemberId(memberOrder.getId());
        order.setNickname(memberOrder.getNickname());
        order.setMobile(memberOrder.getMobile());
        order.setTotalFee(course.getPrice());
        order.setPayType(this.WX_PAY_TYPE_CODE);
        order.setStatus(this.FAILED_STATUS_CODE);
//        4,保存到数据库里
        this.save(order);
//        5，返回订单号
        return order.getOrderNo();
    }

    @Override
    public Order getOrderByNo(String orderNo) {
//        创建查询条件
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no",orderNo);
//        进行查询操作
        return this.getOne(orderQueryWrapper);
    }

    @Override
    public Boolean isBuyCourse(String courseId, String memberId) {
        Boolean isBuy = false;
//        创建查询条件
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("course_id",courseId);
        orderQueryWrapper.eq("member_id",memberId);
//        进行查询
        List<Order> orders = this.list(orderQueryWrapper);
//        判断是购买
        if (orders!=null||orders.size()!=0){
            for (Order order : orders) {
                if (order.getStatus()==1){
                    isBuy =true;
                    break;
                }
            }
        }
        return isBuy;
    }
}
