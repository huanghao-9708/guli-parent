package com.hao.eduservice.controller.front;

import com.hao.eduservice.client.UcenterClient;
import com.hao.eduservice.entity.EduComment;
import com.hao.eduservice.service.EduCommentService;
import com.hao.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：CommentFrontController
 * @description ：TODO
 * @create 2021-01-14-12:47
 */

@RestController
@RequestMapping("/eduservice/front/comment")
public class CommentFrontController {
    private EduCommentService commentService;

    @Autowired
    public void setEduCommentService(EduCommentService commentService) {
        this.commentService = commentService;
    }


    //获取评论列表
    @GetMapping("{current}/{limit}/{courseId}")
    public R index(@PathVariable Integer current,@PathVariable Integer limit,
                   @PathVariable String courseId,HttpServletRequest request){
        Map<String,Object> data = commentService.getCommentPage(current,limit,courseId);
        return R.ok().data(data);
    }
//    添加评论
    @PostMapping("auth/save")
    public R addComment(@RequestBody EduComment comment, HttpServletRequest request){
        return commentService.addComment(comment,request)? R.ok():R.error().msg("请先登录！");
    }
}
