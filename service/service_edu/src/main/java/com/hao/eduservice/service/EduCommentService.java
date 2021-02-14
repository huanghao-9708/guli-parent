package com.hao.eduservice.service;

import com.hao.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author hao
 * @since 2021-01-14
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String,Object> getCommentPage(Integer current, Integer limit, String courseId);

    boolean addComment(EduComment comment, HttpServletRequest request);
}
