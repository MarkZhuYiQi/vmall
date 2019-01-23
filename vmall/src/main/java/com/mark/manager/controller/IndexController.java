package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class IndexController {

    @Value("${header}")
    String header;

    @Reference()
    private CategoryService categoryService;

    @GetMapping("")
    @ResponseBody
    public void home() {
        System.out.println("hello");
        System.out.println(header);
        System.out.println(categoryService.getCategoriesTree());
    }
}
