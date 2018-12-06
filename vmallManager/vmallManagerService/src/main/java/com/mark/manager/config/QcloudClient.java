package com.mark.manager.config;

import com.mark.common.constant.TencentConstant;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;

@Component
public class QcloudClient {
    private static COSClient client = null;
    public static COSClient getCosClient() {
        if (client == null) {
            // 1 初始化用户身份信息(secretId, secretKey)
            COSCredentials cred = new BasicCOSCredentials(TencentConstant.secretId, TencentConstant.secretKey);
            // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
            ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
            // 3 生成cos客户端
            client = new COSClient(cred, clientConfig);
        }
        return client;
    }
    public void setCosClient(COSClient cosClient) {
        client = cosClient;
    }
}
