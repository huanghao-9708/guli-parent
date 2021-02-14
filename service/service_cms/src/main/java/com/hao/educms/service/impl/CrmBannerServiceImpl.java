package com.hao.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.educms.entity.CrmBanner;
import com.hao.educms.mapper.CrmBannerMapper;
import com.hao.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-01-05
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Cacheable(value = "banner",key = "'selectIndexList'")//将其放入redis缓存中
    @Override
    public List<CrmBanner> getFrontBanner() {
        //根据id降序查询前3条banner数据
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 3");
        List<CrmBanner> banners = this.list(wrapper);
        return banners;
    }
}
