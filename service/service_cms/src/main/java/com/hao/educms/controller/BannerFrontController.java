package com.hao.educms.controller;


import com.hao.educms.entity.CrmBanner;
import com.hao.educms.service.CrmBannerService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-01-05
 */
@RestController
@RequestMapping("/educms/banner/front")

public class BannerFrontController {
    private CrmBannerService bannerService;
    @Autowired
    public void setBannerService(CrmBannerService bannerService) {
        this.bannerService = bannerService;
    }

    //获取前端所有前端需要的banner数据
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> banners =  bannerService.getFrontBanner();
        return R.ok().data("banners",banners);
    }
}

