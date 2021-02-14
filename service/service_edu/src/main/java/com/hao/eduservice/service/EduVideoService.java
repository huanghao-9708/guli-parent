package com.hao.eduservice.service;

import com.hao.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author hao
 * @since 2020-12-15
 */
public interface EduVideoService extends IService<EduVideo> {

    List<EduVideo> getVideosByChapterId(String chapterId);

    boolean removeVideoById(String videoId);
    boolean removeVideoByChapterId(String chapterId);
}
