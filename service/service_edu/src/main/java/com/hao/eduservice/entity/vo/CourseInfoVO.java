package com.hao.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：CourseInfoVO
 * @description ：TODO 用于封装接受从前端传来的数据课程数据
 * @create 2020-12-15-15:14
 */
@Data
public class CourseInfoVO {
    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程二级专业ID")
    private String subjectId;

    @ApiModelProperty(value = "课程一级专业ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    // 0.01
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;
}
