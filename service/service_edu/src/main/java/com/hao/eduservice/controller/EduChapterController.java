package com.hao.eduservice.controller;


import com.hao.eduservice.entity.EduChapter;
import com.hao.eduservice.entity.chapter.ChapterVo;
import com.hao.eduservice.service.EduChapterService;
import com.hao.utils.R;
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
@RequestMapping("/eduservice/chapter")

public class EduChapterController {
    private EduChapterService chapterService;
    @Autowired
    public void setChapterService(EduChapterService chapterService) {
        this.chapterService = chapterService;
    }
//    查询指定课程的所有章节
    @GetMapping("getChapterVoById/{courseId}")
    public R getChapterVoById(@PathVariable String courseId){
        List<ChapterVo> chapterVoList = chapterService.getChapterVoById(courseId);
        return R.ok().data("chapterVoList",chapterVoList);
    }
//    查询指定id的章节
    @GetMapping("getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return chapter ==null?R.error():R.ok().data("chapter",chapter);
    }
//    添加章节接口
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        return chapterService.save(chapter)? R.ok():R.error();
    }
//    删除章节接口
    @DeleteMapping("removeChapter/{id}")
    public R removeChapter(@PathVariable String id){
        return chapterService.removeChapterById(id)? R.ok():R.error();
    }
//    修改章节接口
    @PutMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter){
        return chapterService.updateById(chapter)?R.ok():R.error();
    }
}

