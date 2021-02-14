package com.hao.statistics.controller;


import com.hao.statistics.service.StatisticsDailyService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author hao
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/staservice/statistics")

public class StatisticsDailyController {
    private StatisticsDailyService statisticsDailyService;
    @Autowired
    public void setStatisticsDailyService(StatisticsDailyService statisticsDailyService) {
        this.statisticsDailyService = statisticsDailyService;
    }

    //    进行统计某天的数据
    @PostMapping("doStatistics/{day}")
    public R doStatistics(@PathVariable String day){
        statisticsDailyService.doStatistics(day);
        return R.ok();
    }

    @GetMapping("getStatistics/{type}/{begin}/{end}")
    public R getStatistics(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map staData = statisticsDailyService.getStatistics(type,begin,end);
        return R.ok().data("yData",staData.get("yData")).data("xData",staData.get("xData"));
    }
}

