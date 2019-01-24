package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("index")
public class IndexController {

    @Value("${header}")
    String header;

    @Reference()
    private CategoryService categoryService;

    @GetMapping("{navId:\\d+}")
    @ResponseBody
    public void home(@PathVariable Integer navId) {
        System.out.println("hello");
        System.out.println(header);
//        System.out.println(categoryService.getCategoriesTree());
        System.out.println(categoryService.getSubIds(navId));
    }
}
