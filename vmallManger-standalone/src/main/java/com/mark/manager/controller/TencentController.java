package com.mark.manager.controller;

import com.alibaba.fastjson.JSON;
import com.mark.manager.bo.Result;
import com.mark.manager.service.QcloudService;
import com.mark.manager.service.TencentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.*;

@RestController
@RequestMapping("tencent")
public class TencentController {
    @Autowired
    TencentService tencentService;
    @Autowired
    ApplicationContext applicationContext;

    @ResponseBody
    @GetMapping(value = "key", produces="application/json;charset=utf-8")
    public String getTempPermission() {
        String json = tencentService.getTempPermission();
        json = "{\"code\":200, \"data\":" + json + "}";
        return json;
    }

    @Autowired
    QcloudService qcloudService;
    @PostConstruct
    public void filesUpload() {
        String path = "D:\\study163";
        File dir = new File(path);
        File[] fs = dir.listFiles();
        for (File f : fs) {
            if (!f.isDirectory()) {
                FileOutputStream outputStream = null;
                FileInputStream inputStream = null;
                try {
                    FileSystemResource resource = new FileSystemResource(f.getPath());
                    File file = resource.getFile();
                    qcloudService.upload(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}