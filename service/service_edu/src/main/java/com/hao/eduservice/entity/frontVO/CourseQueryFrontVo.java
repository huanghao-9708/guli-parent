package com.hao.eduservice.entity.frontVO;

import lombok.Data;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：CourseQueryVo
 * @description ：TODO
 * @create 2021-01-12-18:52
 */
@Data
public class CourseQueryFrontVo {
    private String id;
    private String title;
    private String teacherId;
    private String SubjectParentId;
    private String SubjectId;
    private String buySort;
    private String priceSort;
    private String gmtCreateSort;
}
