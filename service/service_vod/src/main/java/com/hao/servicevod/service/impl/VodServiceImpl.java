package com.hao.servicevod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hao.servicevod.service.VodService;
import com.hao.servicevod.utils.ConstantPropertiesUtils;
import com.hao.servicevod.utils.InitObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：VodServiceImpl
 * @description ：TODO
 * @create 2021-01-03-13:16
 */
@Service
public class VodServiceImpl implements VodService {
     private String accessKeyId;
     private String accessKeySecret;

    public void init() {
        if (accessKeyId==null || accessKeySecret==null){
            accessKeyId = ConstantPropertiesUtils.KEY_ID;
            accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        }
    }

    @Override
    public String uploadAliyunVideo(MultipartFile file) {
//        初始化变量
        this.init();
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0,fileName.lastIndexOf("."));
        InputStream inputStream = null;
        UploadStreamRequest request = null;
        try {
            inputStream = file.getInputStream();
            request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        上传视频
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return response.getVideoId();
    }

    //删除指定id的视频
    @Override
    public boolean deleteAliyunVideoById(String videoId) {
        ArrayList<String> ids = new ArrayList<>();
        this.deleteAliyunVideoBatch(ids);
        return true;
    }
    //删除指定全部id的视频
    @Override
    public boolean deleteAliyunVideoBatch(List<String> ids){
        //        初始化变量
        this.init();
        String idsStr = "";
        try {
//            获取服务端
            DefaultAcsClient client = InitObject.initVodClient(accessKeyId,accessKeySecret);
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
//            拼接id，用‘,’分开
            for (String id : ids) {
                idsStr +=","+id;
            }
//            去除第一个,
            idsStr = idsStr.substring(1);

            deleteVideoRequest.setVideoIds(idsStr);
            DeleteVideoResponse response = client.getAcsResponse(deleteVideoRequest);
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getPlayAuth(String id) {
        //        初始化变量
        this.init();
//        获取服务端
        try {
            DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);
//        创建获取视频凭证的请求
            GetVideoPlayAuthRequest authRequest = new GetVideoPlayAuthRequest();
            authRequest.setVideoId(id);
//        发送请求获取凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(authRequest);
            String playAuth = response.getPlayAuth();
            return playAuth;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
