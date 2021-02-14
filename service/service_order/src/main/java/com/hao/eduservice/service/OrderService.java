package com.hao.eduservice.service;

import com.hao.eduservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author hao
 * @since 2021-01-19
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberIdByJwtToken);

    Order getOrderByNo(String orderNo);

    Boolean isBuyCourse(String courseId, String memberId);
}
