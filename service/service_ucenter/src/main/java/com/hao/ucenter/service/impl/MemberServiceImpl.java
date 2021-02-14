package com.hao.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.servicebase.config.exceptionHandler.GuliException;
import com.hao.ucenter.entity.Member;
import com.hao.ucenter.entity.vo.RegisterVO;
import com.hao.ucenter.mapper.MemberMapper;
import com.hao.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.ucenter.utils.MD5;
import com.hao.utils.JwtUtils;
import com.hao.utils.orderVo.UcenterMemberOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-01-06
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String login(Member member) {
//        获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //判断密码和手机号是否为空
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
//        根据手机号查询member
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member memberByMobile = this.getOne(wrapper);
//        判断member是否存在
        if (memberByMobile==null){
            throw new GuliException(20001,"登录失败");
        }
//        判断密码是否正确
        if (!MD5.encrypt(password).equals(memberByMobile.getPassword())){
            throw new GuliException(20001,"登录失败");
        }
//        判断是否被禁用
        if (memberByMobile.getIsDisabled()){
            throw new GuliException(20001,"登录失败");
        }
//        获取该对象的token
        String jwtToken = JwtUtils.getJwtToken(memberByMobile.getId(), memberByMobile.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVO registerVO) {
//        获取所有的信息
        String nickName = registerVO.getNickName();
        String mobile = registerVO.getMobile();
        String password = registerVO.getPassword();
        String code = registerVO.getCode();
//        判断是否有空值
        if (StringUtils.isEmpty(nickName)||StringUtils.isEmpty(mobile)||
                StringUtils.isEmpty(password)||StringUtils.isEmpty(code)){
            throw new GuliException(20001,"注册失败");
        }

//        判断短信验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new GuliException(20001,"注册失败");
        }

//        判断手机号是否重复
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        if (this.count(wrapper)>0){
            throw new GuliException(20001,"注册失败");
        }

//        将数据添加到数据库中
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickName);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("https://guli-2020-12.oss-cn-guangzhou.aliyuncs.com/O.png");
        member.setIsDisabled(false);
        this.save(member);

    }

    @Override
    public Member getMemberInfo(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        return this.getById(id);
    }

    @Override
    public Member getMemberByOpenId(String openId) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openId);
        return this.getOne(queryWrapper);
    }

    @Override
    public UcenterMemberOrder getUcenterMemberOrderById(String id) {
        Member member = this.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @Override
    public Integer getCountRegister(String day) {
        return baseMapper.getCountRegister(day);
    }
}
