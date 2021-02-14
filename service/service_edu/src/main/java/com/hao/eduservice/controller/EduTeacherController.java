package com.hao.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.eduservice.entity.vo.QueryTeacher;
import com.hao.utils.R;
import com.hao.eduservice.entity.EduTeacher;
import com.hao.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hao
 * @since 2020-12-03
 */
@Api( description= "讲师的接口")
@RestController
@RequestMapping("/eduservice/teacher")

public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    //获取所有教师接口
    @ApiOperation("查询所有的教师")
    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return R.ok().data("teachers",teachers);
    }
    //删除指定id的讲师
    @ApiOperation("删除指定id的教师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师的id",required = true) @PathVariable String id){
        boolean isRemove = eduTeacherService.removeById(id);
        return isRemove==true?R.ok():R.error();
    }

    @ApiOperation("查询指定条件的教师并进行分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable Integer current,@PathVariable Integer limit,
                                    @RequestBody(required = false) QueryTeacher queryTeacher){
        Page<EduTeacher> page = new Page<>(current, limit);
//        构建查询条件的wrapper
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (queryTeacher.getName()!=null){
            wrapper.like("name",queryTeacher.getName());
        }
        if (queryTeacher.getLevel()!=null){
            wrapper.eq("level",queryTeacher.getLevel());
        }
        if (queryTeacher.getBegin()!=null){
            wrapper.ge("gmt_create",queryTeacher.getBegin());
        }
        if (queryTeacher.getEnd()!=null){
            wrapper.le("gmt_modified",queryTeacher.getEnd());
        }

        IPage<EduTeacher> teacherPage = eduTeacherService.page(page, wrapper);
        return R.ok().data("rows",teacherPage.getRecords()).data("total",teacherPage.getTotal());
    }
    @ApiOperation("添加讲师的接口")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody(required = true) EduTeacher teacher){
        boolean save = eduTeacherService.save(teacher);
        return save?R.ok():R.error();
    }

    @ApiOperation("查询指定id讲师的接口")
    @GetMapping("selectTeacherById/{id}")
    public R selectTeacherById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }
    @ApiOperation("修改讲师的接口")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        boolean update = eduTeacherService.updateById(teacher);
        return update?R.ok():R.error();
    }

}

