package com.hao.eduservice.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：queryTeacher
 * @description ：TODO
 * @create 2020-12-04-17:51
 */
@Api(description = "查询讲师的条件封装")
@Data
public class QueryTeacher {
    @ApiModelProperty(value = "讲师姓名（或对其进行模糊查询）")
    private String name;
    @ApiModelProperty(value = "讲师的等级")
    private Integer level;
    @ApiModelProperty(value = "在此时间之后入驻的所有讲师")
    private Date begin;
    @ApiModelProperty(value = "在此时间之前入驻的所有讲师")
    private Date end;
}
