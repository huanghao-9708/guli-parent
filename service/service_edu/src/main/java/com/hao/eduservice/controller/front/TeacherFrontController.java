package com.hao.eduservice.controller.front;

import com.hao.eduservice.entity.EduCourse;
import com.hao.eduservice.entity.EduTeacher;
import com.hao.eduservice.service.EduCourseService;
import com.hao.eduservice.service.EduTeacherService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：TeacherFrontController
 * @description ：TODO
 * @create 2021-01-12-12:54
 */

@RestController
@RequestMapping("/eduservice/front/teacher")
public class TeacherFrontController {
    private EduTeacherService teacherService;
    private EduCourseService courseService;
    @Autowired
    public void setCourseService(EduCourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setTeacherService(EduTeacherService teacherService) {
        this.teacherService = teacherService;
    }
//    获取前端分页数据
    @GetMapping("getTeacherPage/{current}/{limit}")
    public R getTeacherPage(@PathVariable Integer current,@PathVariable Integer limit){
        Map<String,Object> data = teacherService.getFrontTeacherPage(current,limit);
        return R.ok().data(data);
    }
    //获取教师的详情与他所有的课程信息
    @GetMapping("getTeacherInfoById/{id}")
    public R getTeacherInfoById(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        List<EduCourse> courses =  courseService.getListByTeacherId(id);
        return R.ok().data("teacher",teacher).data("courses",courses);
    }
}
