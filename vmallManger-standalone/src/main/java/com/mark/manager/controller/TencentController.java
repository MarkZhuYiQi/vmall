package com.mark.manager.controller;

import com.alibaba.fastjson.JSON;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.SignatureBody;
import com.mark.manager.service.QcloudService;
import com.mark.manager.service.TencentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "signature")
    public Result getAuthorizationString(@RequestBody SignatureBody signatureBody) {
        return new Result(tencentService.sign(signatureBody));
    }
}