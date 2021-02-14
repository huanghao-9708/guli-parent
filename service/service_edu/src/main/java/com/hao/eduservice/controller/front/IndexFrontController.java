package com.hao.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.eduservice.entity.EduCourse;
import com.hao.eduservice.entity.EduTeacher;
import com.hao.eduservice.service.EduCourseService;
import com.hao.eduservice.service.EduTeacherService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：IndexFrontController
 * @description ：TODO
 * @create 2021-01-05-13:35
 */
@RestController
@RequestMapping("/eduservice/indexfront")

public class IndexFrontController {
    private EduCourseService courseService;
    private EduTeacherService teacherService;
    @Autowired
    public void setCourseService(EduCourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setTeacherService(EduTeacherService teacherService) {
        this.teacherService = teacherService;
    }

    //    获取首页数据 八条热门课程  四条名师
    @GetMapping("index")
    public R index(){

        List<EduCourse> courseList = courseService.getIndexFrontCourse();
        List<EduTeacher> teacherList = teacherService.getIndexFrontTeacher();
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
