package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.pojo.CategoryNode;
import com.mark.manager.bo.Result;
import com.mark.manager.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Reference()
    private CategoryService categoryService;

    @GetMapping("get-nav-tree")
    @ResponseBody
//    public Result getNavTree(@RequestParam("category") Integer navId, @RequestParam("page") Integer page) {
    public Result getNavTree() {
        List<CategoryNode> categoryNodesList = categoryService.getCategoriesTree();
        return new Result(categoryNodesList);
    }
}
