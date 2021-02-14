package com.hao.statistics.schenduling;

import com.hao.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @title: GuliSchedul
 * @Author hao
 * @Date: 2021/1/28 19:15
 * @email: 974721694@qq.com
 * @Version 1.0
 */
@Component
public class GuliScheduled {
    private StatisticsDailyService statisticsDailyService;
    @Autowired
    public void setStatisticsDailyService(StatisticsDailyService statisticsDailyService) {
        this.statisticsDailyService = statisticsDailyService;
    }
//    @Scheduled(cron = "0/1 * * * * ?")
//    public void test(){
//        System.out.println(LocalTime.now()+":xxxx");
//    }
    //每日的1点，统计前一天的的数据
    @Scheduled(cron = "0 0 1 1/1 * ? ")
    public void doSta(){
//        获取前一天的日期
        LocalDate now = LocalDate.now();
        LocalDate lastDay = now.plusDays(-1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
        String day = lastDay.format(formatter);
//        进行统计
        statisticsDailyService.doStatistics(day);
    }
}
