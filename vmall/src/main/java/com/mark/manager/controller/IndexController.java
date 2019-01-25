package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.exception.CategoryException;
import com.mark.common.exception.CourseException;
import com.mark.common.pojo.CategoryNode;
import com.mark.manager.dto.Courses;
import com.mark.manager.pojo.VproNavbar;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("index")
public class IndexController {
    @Value("${header}")
    String header;

    @Reference()
    private CategoryService categoryService;
    @Reference
    private CourseService courseService;

    @GetMapping("{navId:\\d+}")
    @ResponseBody
    public void home(@PathVariable Integer navId) {
        // 测试课程
        Map<Integer, List<Integer>> navIds = null;
        try {
            navIds = categoryService.getSubIds(navId);
        } catch (CategoryException e) {
            e.printStackTrace();
        }
        try {
            Map<Integer, List<Courses>> indexCourses = courseService.getIndexCoursesInfo(navIds);
        } catch (CourseException e) {
            e.printStackTrace();
        }


        // 测试课程导航
        CategoryNode categoryNode = new CategoryNode();
        if (navId == 0) {
            List<CategoryNode> list = categoryService.getCategoriesTree();
            categoryNode.setSubNav(list);
            categoryNode.setNavId(0);
        } else {
            try {
                VproNavbar vproNavbar = categoryService.getCategoryById(navId);
                categoryNode = categoryService.getSubCategory(vproNavbar);
            } catch (CategoryException e) {
                e.printStackTrace();
            }
        }
        System.out.println(categoryNode);
    }
}
