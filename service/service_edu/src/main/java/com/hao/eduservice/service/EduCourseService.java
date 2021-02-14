package com.hao.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hao.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.eduservice.entity.frontVO.CourseQueryFrontVo;
import com.hao.eduservice.entity.frontVO.CourseWebVo;
import com.hao.eduservice.entity.vo.CourseInfoVO;
import com.hao.eduservice.entity.vo.CoursePublishVO;
import com.hao.eduservice.entity.vo.QueryCourse;
import com.hao.utils.orderVo.CourseWebVoOrder;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hao
 * @since 2020-12-15
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseInfoVO courseInfoVO);

    CourseInfoVO getCourseInfoVoById(String id);

    void updateCourse(CourseInfoVO courseInfoVO);

    CoursePublishVO getCoursePublishVOById(String courseId);

    boolean publishCourse(String courseId);

    IPage<EduCourse> pageCourseCondition(Integer current, Integer limit, QueryCourse queryCourse);

    List<EduCourse> getIndexFrontCourse();

    List<EduCourse> getListByTeacherId(String teacherId);

    Map<String, Object> getFrontCoursePage(Integer current, Integer limit, CourseQueryFrontVo courseQueryFrontVo);

    CourseWebVo getBaseCourseInfo(String id);

    CourseWebVoOrder getCourseWebVoById(String id);
}
