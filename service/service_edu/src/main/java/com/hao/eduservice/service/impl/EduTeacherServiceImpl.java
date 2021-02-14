package com.hao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.eduservice.entity.EduTeacher;
import com.hao.eduservice.mapper.EduTeacherMapper;
import com.hao.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author hao
 * @since 2020-12-03
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Cacheable(value = "teacher",key = "'selectIndexList'")
    @Override
    public List<EduTeacher> getIndexFrontTeacher() {
        //        根据id降序，查出前四条讲师数据
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        return this.list(teacherQueryWrapper);
    }

    @Override
    public Map<String, Object> getFrontTeacherPage(Integer current, Integer limit) {
        //获取分页数据对象,根据id排序
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        this.page(teacherPage,teacherQueryWrapper);
//        将其封装到map里
        HashMap<String, Object> data = new HashMap<>();
        data.put("items",teacherPage.getRecords());
        data.put("total",teacherPage.getTotal());
        data.put("size",teacherPage.getSize());
        data.put("pages",teacherPage.getPages());
        data.put("current",teacherPage.getCurrent());
        data.put("hasNext",teacherPage.hasNext());
        data.put("hasPrevious",teacherPage.hasPrevious());
        return data;
    }
}
