package com.mark.manager.service;

import com.mark.common.exception.CategoryException;
import com.mark.common.pojo.CategoryNode;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Navbar;
import com.mark.manager.pojo.VproNavbar;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    VproNavbar getCategoryById(Integer navId) throws CategoryException;
    Navbar getCrumb(Integer navId) throws CategoryException;
    List<VproNavbar> getCrumb(Integer navId, List<VproNavbar> navList) throws CategoryException;
    List<VproNavbar> getCategories();
    Map<Integer, VproNavbar> getCategoriesAsHashMap();
    List<CategoryNode> getCategoriesTree();
    CategoryNode getSubCategory(CategoryNode categoryNode);
    CategoryNode getSubCategory(VproNavbar vproNavbar);
    List<Integer> getSubIdFromCategory(Integer navId, List<VproNavbar> list, List<Integer> idList) throws CategoryException;
    List<Integer> getSubIdFromCategory(Integer navId) throws CategoryException;
    int addCategory(VproNavbar vproNavbar);
    int removeCategory(Integer id);
    int modifyCategory(VproNavbar vproNavbar);
    Map<Integer, List<Integer>> getSubIds() throws CategoryException;
    Map<Integer, List<Integer>> getSubIds(Integer navId) throws CategoryException;
}
