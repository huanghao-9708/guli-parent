package com.hao.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hao.oss.service.OssService;
import com.hao.oss.utils.ConstantPropertiesUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：OssServiceImpl
 * @description ：TODO
 * @create 2020-12-11-19:43
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
// <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
//        按照日期进行分类 文件名是原来的文件名前面加上UUID，防止同名文件被替换
        Calendar rightNow = Calendar.getInstance();
        Integer year = rightNow.get(Calendar.YEAR);
        Integer month = rightNow.get(Calendar.MONTH)+1;
        Integer day = rightNow.get(Calendar.DAY_OF_MONTH);
        String fileName = year+"/"+month+"/"+day+"/" + UUID.randomUUID()+file.getOriginalFilename();

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            ossClient.putObject(bucketName, fileName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

// 关闭OSSClient。
        ossClient.shutdown();
//        拼接上传到oss的图片的url地址
        String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
        return url;
    }
}
