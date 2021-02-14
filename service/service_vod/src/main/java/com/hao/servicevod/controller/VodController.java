package com.hao.servicevod.controller;

import com.hao.servicevod.service.VodService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：VodController
 * @description ：TODO
 * @create 2021-01-03-13:13
 */

@RequestMapping("/eduvod/video")
@RestController()
public class VodController {
    private VodService vodService;
    @Autowired
    public void setVodService(VodService vodService) {
        this.vodService = vodService;
    }

    //    上传视频
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file){
        String videoId = vodService.uploadAliyunVideo(file);
        return R.ok().data("videoId",videoId).data("fileName",file.getOriginalFilename());
    }
//    删除视频
    @DeleteMapping("deleteAliyunVideo/{videoId}")
    public R deleteAliyunVideoById(@PathVariable("videoId") String videoId){
        return vodService.deleteAliyunVideoById(videoId)?R.ok():R.error();
    }
    //    删除多个视频
    @DeleteMapping("deleteAliyunVideoBatch")
    public R deleteAliyunVideoByBatch(@RequestBody List<String> ids){
        return vodService.deleteAliyunVideoBatch(ids)?R.ok():R.error();
    }
    //    获取视频的播放凭证，根据视频id
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        String playAuth = vodService.getPlayAuth(id);
        return R.ok().data("playAuth",playAuth);
    }
}
