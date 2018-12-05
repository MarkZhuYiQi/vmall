package com.mark.manager.controller;

import com.mark.manager.bo.Result;
import com.mark.manager.service.TencentService;
import com.qcloud.Utilities.Json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tencent")
public class TencentController {
    @Autowired
    TencentService tencentService;
    @GetMapping("key")
    public Result getTempPermission() {
        String json = tencentService.getTempPermission();
        return new Result(json);
    }
}
