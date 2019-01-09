package com.mark.manager.controller;

import com.mark.common.pojo.CategoryNode;
import com.mark.manager.bo.Result;
import com.mark.manager.pojo.VproNavbar;
import com.mark.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<VproNavbar> getCategories()
    {
        return categoryService.getCategories();
    }
    @GetMapping("map")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result getCategoriesAsMap()
    {
        return categoryService.getCategoriesAsHashMap();
    }
    @GetMapping("tree")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result getCategoriesTree()
    {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return categoryService.getCategoriesTree();
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("modify")
    public void modifyCategory(@RequestBody VproNavbar vproNavbar) {
        System.out.println(vproNavbar.toString());
    }
//    @PreAuthorize("hasRole('admin')")
    @PostMapping("add")
    public void addCategory(@RequestBody VproNavbar vproNavbar) {
        categoryService.addCategory(vproNavbar);
    }
    @GetMapping("test/{id:\\d+}")
    public void test(@PathVariable("id") Integer navId)
    {
        System.out.println(categoryService.getSubIdFromCategory(navId, categoryService.getCategories(), new ArrayList<Integer>()));
    }
}
