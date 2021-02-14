package com.hao.eduservice.controller.front;

import com.hao.eduservice.client.OrderClient;
import com.hao.eduservice.entity.chapter.ChapterVo;
import com.hao.eduservice.entity.frontVO.CourseQueryFrontVo;
import com.hao.eduservice.entity.frontVO.CourseWebVo;
import com.hao.eduservice.service.EduChapterService;
import com.hao.eduservice.service.EduCourseService;
import com.hao.utils.JwtUtils;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：CourseFrontController
 * @description ：TODO
 * @create 2021-01-12-18:54
 */

@RestController
@RequestMapping("/eduservice/front/course")
public class CourseFrontController {
    private EduCourseService courseService;
    private EduChapterService chapterService;
    private OrderClient orderClient;
    @Autowired
    public void setOrderClient(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @Autowired
    public void setChapterService(EduChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @Autowired
    public void setCourseService(EduCourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("getCourseConditionPage/{current}/{limit}")
    public R getCourseConditionPage(@PathVariable Integer current,@PathVariable Integer limit,
                                        @RequestBody(required = false) CourseQueryFrontVo courseQueryFrontVo){
        Map<String,Object> data = courseService.getFrontCoursePage(current,limit,courseQueryFrontVo);
        return R.ok().data(data);
    }
    @PostMapping("getBaseCourseInfo/{id}")
    public R getBaseCourseInfo(@PathVariable String id,HttpServletRequest request){
//        获取课程基本信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(id);
//        获取课程的章节目录
        List<ChapterVo> chapterVoList = chapterService.getChapterVoById(id);
//        解析出用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//        判断用户是否已经购买该课程
        Boolean isBuy = false;
        if (!StringUtils.isEmpty(memberId)){
            isBuy = this.orderClient.isBuyCourse(id,memberId);
        }
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVoList",chapterVoList).data("isBuy",isBuy);
    }
}
