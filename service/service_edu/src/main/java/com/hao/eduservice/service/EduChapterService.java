package com.hao.eduservice.service;

import com.hao.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hao
 * @since 2020-12-15
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVoById(String courseId);

    boolean removeChapterById(String id);
}
