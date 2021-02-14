package com.hao.statistics.service.impl;

import com.alibaba.nacos.client.naming.utils.RandomUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.statistics.client.UcenterClient;
import com.hao.statistics.entity.StatisticsDaily;
import com.hao.statistics.mapper.StatisticsDailyMapper;
import com.hao.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-01-28
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    private UcenterClient ucenterClient;
    @Autowired
    public void setUcenterClient(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }

    @Override
    public void doStatistics(String day) {
//        统计之前先删除指定的统计的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        this.remove(wrapper);
//        获取当天的注册人数
        R registerR = ucenterClient.getCountRegister(day);
        Integer registerCount = (Integer) registerR.getData().get("count");
//        获取当天的登录人数
        Integer loginCount = RandomUtils.nextInt(1000);
//        获取当天的视频观看人数
        Integer watchCount = RandomUtils.nextInt(1000);
//        获取当天的课程新增
        Integer courseAddCount = RandomUtils.nextInt(1000);
//        封装成统计数据对象
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registerCount);
        statisticsDaily.setLoginNum(loginCount);
        statisticsDaily.setVideoViewNum(watchCount);
        statisticsDaily.setCourseNum(courseAddCount);
        statisticsDaily.setDateCalculated(day);
//        进行包存
        this.save(statisticsDaily);
    }

    @Override
    public Map getStatistics(String type, String begin, String end) {
        //查询结构
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.orderByAsc("date_calculated");
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> list = this.list(wrapper);
        //封装成合适的数据结构(数组结构)，翻遍做为echart的数据
        List<String> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();

        for (StatisticsDaily statisticsDaily : list) {
            xData.add(statisticsDaily.getDateCalculated());
            switch (type){
                case "register_num":
                    yData.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    yData.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    yData.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    yData.add(statisticsDaily.getCourseNum());
                    break;
            }
        }
        HashMap map = new HashMap<>();
        map.put("xData",xData);
        map.put("yData",yData);
        return map;
    }
}
