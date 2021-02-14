package com.hao.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.eduservice.entity.EduSubject;
import com.hao.eduservice.entity.excel.SubjectData;
import com.hao.eduservice.mapper.EduSubjectMapper;
import com.hao.eduservice.service.EduSubjectService;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：SubjectExcelListener
 * @description ：TODO
 * @create 2020-12-13-20:36
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    private  EduSubjectService eduSubjectService;
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext analysisContext) {
        //添加一级分类，如果有则不添加
        String oneSubjectName = data.getOneSubject();
        EduSubject eduSubject1 = existOneSub(oneSubjectName);
        if (eduSubject1==null){
            eduSubject1 = new EduSubject();
            eduSubject1.setTitle(oneSubjectName);
            eduSubject1.setParentId("0");
            eduSubjectService.save(eduSubject1);
        }

//        获取父分类的id
        String pid = eduSubject1.getId();
//        添加二级分类，如果有则不添加
        String twoSubjectName = data.getTwoSubject();
        EduSubject eduSubject2 = existTwoSub(twoSubjectName, pid);
        if (eduSubject2==null){
            eduSubject2 = new EduSubject();
            eduSubject2.setTitle(twoSubjectName);
            eduSubject2.setParentId(pid);
            eduSubjectService.save(eduSubject2);
        }
    }
//    检查一级分类是否重复
    public EduSubject existOneSub(String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        return eduSubjectService.getOne(wrapper);
    }
//    检查二级分类是否重复
    public EduSubject existTwoSub(String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        return eduSubjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
