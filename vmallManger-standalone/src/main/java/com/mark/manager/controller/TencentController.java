package com.mark.manager.controller;

import com.alibaba.fastjson.JSON;
import com.mark.manager.bo.Result;
import com.mark.manager.service.TencentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tencent")
public class TencentController {
    @Autowired
    TencentService tencentService;

    @ResponseBody
    @GetMapping(value = "key", produces="application/json;charset=utf-8")
    public String getTempPermission() {
        String json = tencentService.getTempPermission();
        json = "{\"code\":200, \"data\":" + json + "}";
        return json;
    }
}
