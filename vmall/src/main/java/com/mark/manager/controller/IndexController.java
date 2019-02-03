package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.exception.CategoryException;
import com.mark.common.exception.CourseException;
import com.mark.common.pojo.CategoryNode;
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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("index")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Value("${header}")
    String header;

    @Reference()
    private TestService testService;
    @Reference()
    private CategoryService categoryService;
    @Reference()
    private CourseService courseService;

    @GetMapping("test/{type:\\w+}")
    @ResponseBody
    public Result test(@PathVariable String type) {
        long res = 0;
        switch(type) {
            case "directExchange":
                res = testService.directExchange();
                break;
            case "directExchangeReceiving":
                try {
                    res = testService.directExchangeReceiving();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "workQueues":
                testService.workQueues("work.");
                break;
            case "workQueuesReceiving":
                testService.workQueuesReceiving();
                break;
            case "subscribe":
                testService.subscribe();
                break;
            case "publish":
                testService.publish();
                break;
            case "topic":
                testService.topic();
                break;
            case "topicReceiving":
                testService.topicReceiving();
                break;
        }
        return new Result(res);
    }

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
        Map<Integer, List<Courses>> indexCourses;
        long courseTime = System.currentTimeMillis();
        try {
            if (courseService.indexCoursesIsExisted(navId)) {
                logger.info("indexCourses info get from redis");
                indexCourses = courseService.getIndexCoursesInfoCache(navId);
            } else {
                // 封面课程
                Map<Integer, List<Integer>> navIds = categoryService.getSubIds(navId);
                indexCourses = courseService.getIndexCoursesInfo(navId, navIds);
            }
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
}
