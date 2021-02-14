package com.hao.eduservice.service;

import com.hao.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.eduservice.entity.data.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author hao
 * @since 2020-12-13
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file);

    List<OneSubject> getAllSubject();
}
