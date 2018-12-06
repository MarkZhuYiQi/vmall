package com.mark.manager.serviceImpl;

import com.mark.common.constant.TencentConstant;
import com.mark.manager.config.QcloudClient;
import com.mark.manager.service.QcloudService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class QcloudServiceImpl implements QcloudService {

    // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
    private String bucketName = "vpro-1258194404";

    public void upload(File file) throws IOException {
        COSClient cosClient = QcloudClient.getCosClient();
        // 方法2 从输入流上传(需提前告知输入流的长度, 否则可能导致 oom)
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectMetadata objectMetadata = new ObjectMetadata();
// 设置输入流长度为500
        objectMetadata.setContentLength(file.length());
// 设置 Content type, 默认是 application/octet-stream
//        objectMetadata.setContentType("application/pdf");

        PutObjectResult putObjectResult = cosClient.putObject(bucketName, file.getName(), fileInputStream, objectMetadata);
        String etag = putObjectResult.getETag();
// 关闭输入流...
        fileInputStream.close();
        System.out.println(etag);
    }
}
