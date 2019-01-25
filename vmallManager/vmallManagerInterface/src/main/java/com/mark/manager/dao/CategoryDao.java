package com.mark.manager.dao;

import com.mark.common.exception.CategoryException;
import com.mark.common.pojo.CategoryNode;
import com.mark.manager.pojo.VproNavbar;

import java.util.List;

public interface CategoryDao {
    List<VproNavbar> getCategories();
    VproNavbar getCategoryById(Integer navId) throws CategoryException;
    List<CategoryNode> getCategoriesTree(List<VproNavbar> navbars) throws CategoryException;
}
