package com.mark.manager.service;

import com.qcloud.cos.COSClient;

import java.io.File;
import java.io.IOException;

public interface QcloudService {
    void upload(File file) throws IOException;
}
