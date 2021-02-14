package com.hao.eduservice.entity.chapter;

import com.hao.eduservice.entity.EduVideo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：ChapterVideoVo
 * @description ：TODO
 * @create 2020-12-21-15:53
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
