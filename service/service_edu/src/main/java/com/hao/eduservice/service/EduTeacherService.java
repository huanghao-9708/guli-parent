package com.hao.eduservice.service;

import com.hao.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author hao
 * @since 2020-12-03
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getIndexFrontTeacher();

    Map<String, Object> getFrontTeacherPage(Integer current, Integer limit);
}
