package com.hao.educms.service;

import com.hao.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author hao
 * @since 2021-01-05
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getFrontBanner();
}
