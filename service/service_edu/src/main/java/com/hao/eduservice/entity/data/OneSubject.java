package com.hao.eduservice.entity.data;

import com.hao.eduservice.entity.excel.SubjectData;
import lombok.Data;

import javax.security.auth.Subject;
import java.util.List;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：SubjectData
 * @description ：TODO
 * @create 2020-12-14-15:22
 */
@Data
public class OneSubject{
    private String id;
    private String label;
    private List<TwoSubject> children;
}
