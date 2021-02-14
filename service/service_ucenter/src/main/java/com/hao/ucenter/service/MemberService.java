package com.hao.ucenter.service;

import com.hao.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.ucenter.entity.vo.RegisterVO;
import com.hao.utils.orderVo.UcenterMemberOrder;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-01-06
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVO registerVO);

    Member getMemberInfo(HttpServletRequest request);

    Member getMemberByOpenId(String openId);

    UcenterMemberOrder getUcenterMemberOrderById(String id);

    Integer getCountRegister(String day);
}
