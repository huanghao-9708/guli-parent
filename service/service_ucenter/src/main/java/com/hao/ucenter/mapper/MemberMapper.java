package com.hao.ucenter.mapper;

import com.hao.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author hao
 * @since 2021-01-06
 */
public interface MemberMapper extends BaseMapper<Member> {
    //统计指定日期的所有注册人数
    Integer getCountRegister(String day);
}
