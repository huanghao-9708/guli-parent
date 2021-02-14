package com.hao.eduservice.controller;


import com.alibaba.excel.EasyExcel;
import com.hao.eduservice.entity.data.OneSubject;
import com.hao.eduservice.entity.excel.SubjectData;
import com.hao.eduservice.listener.SubjectExcelListener;
import com.hao.eduservice.service.EduSubjectService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author hao
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/eduservice/subject")

public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //上传一个excel文件用来添加课程分类
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file);
        return R.ok();
    }
    //获取展示课程分类列表
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> oneSubjects = subjectService.getAllSubject();
        return R.ok().data("oneSubjects",oneSubjects);
    }
}

