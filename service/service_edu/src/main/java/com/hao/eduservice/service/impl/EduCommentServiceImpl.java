package com.hao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.eduservice.client.UcenterClient;
import com.hao.eduservice.entity.EduComment;
import com.hao.eduservice.mapper.EduCommentMapper;
import com.hao.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.utils.JwtUtils;
import com.hao.utils.R;
import com.hao.utils.orderVo.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author hao
 * @since 2021-01-14
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
    private UcenterClient ucenterClient;
    @Autowired
    public void setUcenterClient(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }
    @Override
    public Map<String,Object> getCommentPage(Integer current, Integer limit, String courseId) {
//        创建分页对象和查询条件
        Page<EduComment> commentPage = new Page<>(current,limit);
        QueryWrapper<EduComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id",courseId);
        this.page(commentPage,commentQueryWrapper);
        //        将其封装到map里
        HashMap<String, Object> data = new HashMap<>();
        data.put("items",commentPage.getRecords());
        data.put("total",commentPage.getTotal());
        data.put("size",commentPage.getSize());
        data.put("pages",commentPage.getPages());
        data.put("current",commentPage.getCurrent());
        data.put("hasNext",commentPage.hasNext());
        data.put("hasPrevious",commentPage.hasPrevious());
        return data;
    }

    @Override
    public boolean addComment(EduComment comment, HttpServletRequest request) {
//        根据token 获取用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(id)){
            return false;
        }
        //根据用户id获取用户信息 将其指定信息放入评论中
        UcenterMemberOrder memberOrder = ucenterClient.getMemberInfoById(id);
        comment.setMemberId(memberOrder.getId());
        comment.setNickname(memberOrder.getNickname());
        comment.setAvatar(memberOrder.getAvatar());
        //保存评论
        this.save(comment);
        return true;
    }
}
