package com.hao.eduservice.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：QueryCourse
 * @description ：TODO
 * @create 2021-01-02-9:59
 */
@Data
public class QueryCourse {
    private String title;
    private String teacherName;
    private Date createBegin;
    private Date createEnd;
}
