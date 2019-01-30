package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.exception.CategoryException;
import com.mark.common.exception.CourseException;
import com.mark.common.pojo.CategoryNode;
import com.mark.manager.bo.IndexResult;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Courses;
import com.mark.manager.pojo.VproNavbar;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import com.mark.manager.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("index")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Value("${header}")
    String header;

    @Reference()
    private CategoryService categoryService;
    @Reference
    private CourseService courseService;
    @Reference
    private TestService testService;

    @GetMapping("nav/{navId:\\d+}")
    @ResponseBody
    public Result nav(@PathVariable Integer navId) {
        CategoryNode categoryNode = new CategoryNode();
        try {
            long navTime = System.currentTimeMillis();
            // 导航
            if (navId == 0) {
                List<CategoryNode> list = categoryService.getCategoriesTree();
                categoryNode.setSubNav(list);
                categoryNode.setNavId(0);
            } else {
                VproNavbar vproNavbar = categoryService.getCategoryById(navId);
                categoryNode = categoryService.getSubCategory(vproNavbar);
            }
            System.out.println("导航数据消耗时间：" + String.valueOf(System.currentTimeMillis() - navTime) + "ms");
            return new Result(categoryNode);
        } catch (CategoryException ee) {
            logger.warn(ee.getMsg());
            return new Result(ee.getCode(), ee.getMessage());
        }
    }
    @GetMapping("courses/{navId:\\d+}")
    @ResponseBody
    public Result indexCourses(@PathVariable Integer navId) {
        try {
            long courseTime = System.currentTimeMillis();
            // 封面课程
            Map<Integer, List<Integer>> navIds = categoryService.getSubIds(navId);
            Map<Integer, List<Courses>> indexCourses = courseService.getIndexCoursesInfo(navIds);

            System.out.println("课程数据消耗时间：" + String.valueOf(System.currentTimeMillis() - courseTime) + "ms");

            return new Result(indexCourses);
        } catch (CategoryException ee) {
            logger.warn(ee.getMsg());
            return new Result(ee.getCode(), ee.getMessage());
        } catch (CourseException ec) {
            logger.warn(ec.getMsg());
            return new Result(ec.getCode(), ec.getMessage());
        }
    }
    /**
     * 导航和课程捆绑销售，发现时间很长啊
     * @param navId
     * @return
     */
/*
    @GetMapping("{navId:\\d+}")
    @ResponseBody
    public Result indexnav(@PathVariable Integer navId) {
        IndexResult indexResult = new IndexResult();
        CategoryNode categoryNode = new CategoryNode();
        try {

            long time = System.currentTimeMillis();
            long navTime = System.currentTimeMillis();

            // 导航
            if (navId == 0) {
                List<CategoryNode> list = categoryService.getCategoriesTree();
                categoryNode.setSubNav(list);
                categoryNode.setNavId(0);
            } else {
                VproNavbar vproNavbar = categoryService.getCategoryById(navId);
                categoryNode = categoryService.getSubCategory(vproNavbar);
            }

            System.out.println("导航数据消耗时间：" + String.valueOf(System.currentTimeMillis() - navTime) + "ms");

            indexResult.setNav(categoryNode);

            long courseTime = System.currentTimeMillis();
            // 封面课程
            Map<Integer, List<Integer>> navIds = categoryService.getSubIds(navId);
            Map<Integer, List<Courses>> indexCourses = courseService.getIndexCoursesInfo(navIds);

            System.out.println("课程数据消耗时间：" + String.valueOf(System.currentTimeMillis() - courseTime) + "ms");

            indexResult.setCourses(indexCourses);

            System.out.println("总时间： " + String.valueOf(System.currentTimeMillis() - time) + "ms");

            return new Result(indexResult);
        } catch (CategoryException ee) {
            logger.warn(ee.getMsg());
            return new Result(ee.getCode(), ee.getMessage());
        } catch (CourseException ec) {
            logger.warn(ec.getMsg());
            return new Result(ec.getCode(), ec.getMessage());
        }
    }
*/

/*    @GetMapping("{navId:\\d+}")
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
            System.out.println(indexCourses);
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
    }*/
}
