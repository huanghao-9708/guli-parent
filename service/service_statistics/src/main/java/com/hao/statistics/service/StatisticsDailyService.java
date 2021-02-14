package com.hao.statistics.service;

import com.hao.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author hao
 * @since 2021-01-28
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void doStatistics(String day);

    Map getStatistics(String type, String begin, String end);
}
