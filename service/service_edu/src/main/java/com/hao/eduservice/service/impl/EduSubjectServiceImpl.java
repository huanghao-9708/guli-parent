package com.hao.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hao.eduservice.entity.EduSubject;
import com.hao.eduservice.entity.data.OneSubject;
import com.hao.eduservice.entity.data.TwoSubject;
import com.hao.eduservice.entity.excel.SubjectData;
import com.hao.eduservice.listener.SubjectExcelListener;
import com.hao.eduservice.mapper.EduSubjectMapper;
import com.hao.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hao
 * @since 2020-12-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public void saveSubject(MultipartFile file) {
        //        获取文件上传输入流
        InputStream fileInputStream = null;
        try {
            fileInputStream = file.getInputStream();
            EasyExcel.read(fileInputStream, SubjectData.class,new SubjectExcelListener(this)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
//        查询所有一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneEduSubjects = baseMapper.selectList(oneWrapper);

        //        查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoEduSubjects = baseMapper.selectList(twoWrapper);
//        将一级分类封装到oneSubject里并添加到List里面
        ArrayList<OneSubject> oneSubjects = new ArrayList<>();
        for (EduSubject e : oneEduSubjects) {
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(e.getId());
            oneSubject.setLabel(e.getTitle());

            ArrayList<TwoSubject> twoSubjects = new ArrayList<>();
//        将二级分类封装到相对应的twoSubject里并添加到List里
            for (EduSubject twoE : twoEduSubjects) {
                if (e.getId().equals(twoE.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    twoSubject.setId(twoE.getId());
                    twoSubject.setLabel(twoE.getTitle());
                    twoSubjects.add(twoSubject);
                }
            }
//            将List放入一级分类的children里
            oneSubject.setChildren(twoSubjects);
            oneSubjects.add(oneSubject);

        }
        return oneSubjects;
    }
}
