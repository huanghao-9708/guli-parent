package com.hao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.eduservice.client.VodClient;
import com.hao.eduservice.entity.EduVideo;
import com.hao.eduservice.mapper.EduVideoMapper;
import com.hao.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.servicebase.config.exceptionHandler.GuliException;
import com.hao.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author hao
 * @since 2020-12-15
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    private VodClient vodClient;
    @Autowired
    public void setVodClient(VodClient vodClient) {
        this.vodClient = vodClient;
    }
    @Override
    public List<EduVideo> getVideosByChapterId(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        List<EduVideo> videos = this.list(queryWrapper);
        return videos;
    }

    @Override
    public boolean removeVideoById(String videoId) {
//        获取小结的视频源id
        EduVideo video = this.getById(videoId);
        String sourceId = video.getVideoSourceId();
//        删除视频
        if (!StringUtils.isEmpty(sourceId)){
            R msg = vodClient.deleteAliyunVideoById(sourceId);
            if(msg.getCode().equals(20001)) {
                throw new GuliException(20001, "删除失败");
            }
        }
//        删除小结
        return this.removeById(videoId);
    }
    @Override
    public boolean removeVideoByChapterId(String chapterId) {
//        获取该章节所有的小结
        List<EduVideo> videos = this.getVideosByChapterId(chapterId);
//        将其id合成一个集合
        ArrayList<String> ids = new ArrayList<>();
        for (EduVideo video : videos) {
            ids.add(video.getId());
        }
//        调用删除接口
        R msg = vodClient.deleteAliyunVideoByBatch(ids);
        if(msg.getCode().equals(20001)){
            throw new GuliException(20001,"删除失败");
        }
        return true;
    }
}
