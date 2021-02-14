package com.hao.eduservice.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：CoursePublishVO
 * @description ：课程去人页面的数据模型
 * @create 2020-12-31-10:46
 */
@Data
public class CoursePublishVO {
    private String id;
    private String title;
    private Integer lessonNum;
    private String cover;
    private BigDecimal price;
    private String teacher;
    private String description;
    private String subject;
    private String subjectParent;
}
