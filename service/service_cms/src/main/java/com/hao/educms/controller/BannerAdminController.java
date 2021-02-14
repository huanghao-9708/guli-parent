package com.hao.educms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.educms.entity.CrmBanner;
import com.hao.educms.service.CrmBannerService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 的后端管理
 * </p>
 *
 * @author hao
 * @since 2021-01-05
 */
@RestController
@RequestMapping("/educms/banner/admin")
public class BannerAdminController {
    private CrmBannerService bannerService;
    @Autowired
    public void setBannerService(CrmBannerService bannerService) {
        this.bannerService = bannerService;
    }
    //分页查询banner
    @GetMapping("pageBannerCondition/{current}/{limit}")
    public R pageBannerCondition(@PathVariable Integer current,@PathVariable Integer limit){
        Page<CrmBanner> page = new Page<>(current, limit);
        IPage<CrmBanner> bannerIPage = bannerService.page(page, null);
        return R.ok().data("items",bannerIPage.getRecords()).data("total",bannerIPage.getTotal());
    }
//    新增banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner banner){
        return bannerService.save(banner)?R.ok():R.error();
    }
//    新增banner
    @DeleteMapping("removeBanner/{id}")
    public R removeBanner(String id){
        return bannerService.removeById(id)?R.ok():R.error();
    }
//    新增banner
    @PutMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner){
        return bannerService.updateById(banner)?R.ok():R.error();
    }
}

