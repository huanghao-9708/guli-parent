package com.hao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.eduservice.entity.EduCourse;
import com.hao.eduservice.entity.EduCourseDescription;
import com.hao.eduservice.entity.EduTeacher;
import com.hao.eduservice.entity.frontVO.CourseQueryFrontVo;
import com.hao.eduservice.entity.frontVO.CourseWebVo;
import com.hao.eduservice.entity.vo.CourseInfoVO;
import com.hao.eduservice.entity.vo.CoursePublishVO;
import com.hao.eduservice.entity.vo.QueryCourse;
import com.hao.eduservice.mapper.EduCourseMapper;
import com.hao.eduservice.service.EduCourseDescriptionService;
import com.hao.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.eduservice.service.EduTeacherService;
import com.hao.utils.orderVo.CourseWebVoOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hao
 * @since 2020-12-15
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    private EduCourseDescriptionService eduCourseDescriptionService;
    private EduTeacherService eduTeacherService;
    @Autowired
    public void setEduTeacherService(EduTeacherService eduTeacherService) {
        this.eduTeacherService = eduTeacherService;
    }

    @Autowired
    public void setEduCourseDescriptionService(EduCourseDescriptionService eduCourseDescriptionService) {
        this.eduCourseDescriptionService = eduCourseDescriptionService;
    }

    @Override
    public String addCourse(CourseInfoVO courseInfoVO) {
//        将课程信息封装
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,course);
        this.save(course);
        //        获取课程的id
        String courseId = course.getId();
//        以课程的id 加课程描述信息一起封装为课程描述对象
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseId);
        description.setDescription(courseInfoVO.getDescription());
//        将课程信息对象，课程描述对象都放入数据库中
        eduCourseDescriptionService.save(description);
        return courseId;
    }

    @Override
    public CourseInfoVO getCourseInfoVoById(String id) {
        //获取课程对象
        EduCourse course = this.getById(id);
        //获取课程描述对象
        EduCourseDescription description = eduCourseDescriptionService.getById(id);
        //将其封装成课程信息对象
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtils.copyProperties(course,courseInfoVO);
        courseInfoVO.setDescription(description.getDescription());
        return courseInfoVO;
    }

    @Override
    public void updateCourse(CourseInfoVO courseInfoVO) {
        //        将课程信息封装
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,course);
//        将课程描述信息封装
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVO,description);
//        分别更新
        this.updateById(course);
        eduCourseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVO getCoursePublishVOById(String courseId) {
        return baseMapper.getCoursePublishVOById(courseId);
    }

    @Override
    public boolean publishCourse(String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
//        Normal表示已经发布
        course.setStatus("Normal");
        return this.updateById(course);
    }

    @Override
    public IPage<EduCourse> pageCourseCondition(Integer current, Integer limit, QueryCourse queryCourse) {
        Page<EduCourse> page = new Page<>(current,limit);
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        if (queryCourse.getTitle()!=null){
            courseQueryWrapper.like("title",queryCourse.getTitle());
        }
        if (queryCourse.getTeacherName()!=null){
//            先获取所有like teacherName的教师id
            QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
            teacherQueryWrapper.like("name",queryCourse.getTeacherName());
            List<EduTeacher> teachers = eduTeacherService.list(teacherQueryWrapper);

            for (EduTeacher teacher : teachers) {
                courseQueryWrapper.eq("teacher_id",teacher.getId());
                courseQueryWrapper.or();
            }
        }
        if (queryCourse.getCreateBegin()!=null){
            courseQueryWrapper.ge("gmt_create",queryCourse.getCreateBegin());
        }
        if (queryCourse.getCreateEnd()!=null){
            courseQueryWrapper.le("gmt_create",queryCourse.getCreateEnd());
        }

        return this.page(page, courseQueryWrapper);
    }

    @Cacheable(value = "course",key = "'selectIndexList'")
    @Override
    public List<EduCourse> getIndexFrontCourse() {
        //        根据id降序，查出前八条课程数据
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        return this.list(courseQueryWrapper);
    }

    @Override
    public List<EduCourse> getListByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",teacherId);
        courseQueryWrapper.orderByDesc("gmt_create");
        List<EduCourse> courses = this.list(courseQueryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> getFrontCoursePage(Integer current, Integer limit, CourseQueryFrontVo courseQueryFrontVo) {
        Page<EduCourse> coursePage = new Page<>(current,limit);
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQueryFrontVo.getSubjectParentId())){
            courseQueryWrapper.eq("subject_parent_id",courseQueryFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQueryFrontVo.getSubjectId())){
            courseQueryWrapper.eq("subject_id",courseQueryFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQueryFrontVo.getBuySort())){
            courseQueryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQueryFrontVo.getGmtCreateSort())){
            courseQueryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQueryFrontVo.getPriceSort())){
            courseQueryWrapper.orderByDesc("price");
        }
        this.page(coursePage,courseQueryWrapper);
        //        将其封装到map里
        HashMap<String, Object> data = new HashMap<>();
        data.put("items",coursePage.getRecords());
        data.put("total",coursePage.getTotal());
        data.put("size",coursePage.getSize());
        data.put("pages",coursePage.getPages());
        data.put("current",coursePage.getCurrent());
        data.put("hasNext",coursePage.hasNext());
        data.put("hasPrevious",coursePage.hasPrevious());
        return data;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String id) {
        return baseMapper.getBaseCourseInfo(id);
    }

    @Override
    public CourseWebVoOrder getCourseWebVoById(String id) {
        EduCourse course = this.getById(id);
        EduTeacher teacher = eduTeacherService.getById(course.getTeacherId());
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(course,courseWebVoOrder);
        courseWebVoOrder.setTeacherName(teacher.getName());
        return courseWebVoOrder;
    }
}
