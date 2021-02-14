package com.hao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.eduservice.client.VodClient;
import com.hao.eduservice.entity.EduChapter;
import com.hao.eduservice.entity.EduVideo;
import com.hao.eduservice.entity.chapter.ChapterVo;
import com.hao.eduservice.entity.chapter.VideoVo;
import com.hao.eduservice.mapper.EduChapterMapper;
import com.hao.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.eduservice.service.EduVideoService;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hao
 * @since 2020-12-15
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    private EduVideoService videoService;

    @Autowired
    public void setVideoService(EduVideoService videoService) {
        this.videoService = videoService;
    }

    //根据课程的id 查询章节和视频信息
    @Override
    public List<ChapterVo> getChapterVoById(String courseId) {
//        1，获取所有该课程id的章节并封装到Vo对象中
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> chapters = this.list(chapterWrapper);
        for (EduChapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            chapterVos.add(chapterVo);
        }
//        2，获取所有该乘车id的video并封装到Vo对象中
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduVideo> videos = videoService.list(videoWrapper);
        for (EduVideo video : videos) {
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(video,videoVo);
//        3，将video的Vo封装到ChapterVo中去
            for (ChapterVo chapterVo: chapterVos) {
                if (video.getChapterId().equals(chapterVo.getId())){
                    chapterVo.getChildren().add(videoVo);
                }
            }
        }

        return chapterVos;
    }

    @Override
    public boolean removeChapterById(String id) {
        videoService.getVideosByChapterId(id);
//        删除小结
        return this.removeById(id);
    }
}
