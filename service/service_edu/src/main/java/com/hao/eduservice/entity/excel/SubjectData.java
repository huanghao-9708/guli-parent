package com.hao.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：SubjectData
 * @description ：TODO
 * @create 2020-12-13-20:24
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubject;
    @ExcelProperty(index = 1)
    private String twoSubject;
}
