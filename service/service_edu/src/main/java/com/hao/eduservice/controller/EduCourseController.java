package com.hao.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hao.eduservice.entity.EduCourse;
import com.hao.eduservice.entity.vo.CourseInfoVO;
import com.hao.eduservice.entity.vo.CoursePublishVO;
import com.hao.eduservice.entity.vo.QueryCourse;
import com.hao.eduservice.service.EduChapterService;
import com.hao.eduservice.service.EduCourseService;
import com.hao.utils.R;
import com.hao.utils.orderVo.CourseWebVoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hao
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/eduservice/course")

public class EduCourseController {
    private EduCourseService eduCourseService;
    @Autowired
    public void setEduCourseService(EduCourseService eduCourseService) {
        this.eduCourseService = eduCourseService;
    }
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable Integer current,@PathVariable Integer limit
                                        ,@RequestBody(required = false) QueryCourse queryCourse) {
        IPage<EduCourse> courseIPage = eduCourseService.pageCourseCondition(current, limit, queryCourse);
        return R.ok().data("courses",courseIPage.getRecords()).data("total",courseIPage.getTotal());
    }
//    删除课程
    @DeleteMapping("removeCourse/{courseId}")
    public R removeCourseById(@PathVariable String courseId){
        return eduCourseService.removeById(courseId)?R.ok():R.error();
    }
    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfoVO courseInfoVO){
        String cid = eduCourseService.addCourse(courseInfoVO);
        return R.ok().data("cid",cid);
    }
//    根据id查询课程信息
    @GetMapping("getCourseInfoVo/{id}")
    public R getCourseInfoVo(@PathVariable String id){
        CourseInfoVO courseInfoVO = eduCourseService.getCourseInfoVoById(id);
        return R.ok().data("courseInfoVO",courseInfoVO);
    }
//    根据id修改课程信息
    @PutMapping("updateCourse")
    public R updateCourse(@RequestBody CourseInfoVO courseInfoVO){
        eduCourseService.updateCourse(courseInfoVO);
        return R.ok();
    }
//    根据课程id查询课程相关信息
    @GetMapping("getCoursePublishVOById/{courseId}")
    public R getCoursePublishVOById(@PathVariable String courseId){
        CoursePublishVO coursePublishVO = eduCourseService.getCoursePublishVOById(courseId);
        return R.ok().data("coursePublishVO",coursePublishVO);
    }
    //发布课程
    @PutMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        return eduCourseService.publishCourse(courseId)?R.ok():R.error();
    }
//    为其他服务进行课程查询
    @GetMapping("getCourse/{id}")
    public CourseWebVoOrder getCourse(@PathVariable String id){
        CourseWebVoOrder courseWebVoOrder = eduCourseService.getCourseWebVoById(id);
        return courseWebVoOrder;
    }
}

