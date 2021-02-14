package com.hao.eduservice.controller;


import com.hao.eduservice.entity.EduVideo;
import com.hao.eduservice.service.EduVideoService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author hao
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/eduservice/video")

public class EduVideoController {
    private EduVideoService videoService;
    @Autowired
    public void setVideoService(EduVideoService videoService) {
        this.videoService = videoService;
    }

    //根据章节id查询所有的video
    @GetMapping("getVideosByChapterId/{chapterId}")
    public R getVideosByChapterId(@PathVariable String chapterId){
        List<EduVideo> videos =  videoService.getVideosByChapterId(chapterId);
        return R.ok().data("videos",videos);
    }
    //根据video的id来查询video
    @GetMapping("getVideoById/{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }
//    新增video
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video){
        return videoService.save(video)?R.ok():R.error();
    }
//    删除video
    @DeleteMapping("removeVideoById/{videoId}")
    public R removeVideoById(@PathVariable String videoId){
        return videoService.removeVideoById(videoId)?R.ok():R.error();
    }
//    修改video
    @PutMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        return videoService.updateById(video)?R.ok():R.error();
    }

}

