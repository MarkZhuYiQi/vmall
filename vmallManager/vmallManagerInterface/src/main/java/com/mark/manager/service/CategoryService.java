package com.mark.manager.service;

import com.mark.common.exception.CategoryException;
import com.mark.common.pojo.CategoryNode;
import com.mark.manager.bo.Result;
import com.mark.manager.pojo.VproNavbar;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<VproNavbar> getCategories();
    Map<Integer, VproNavbar> getCategoriesAsHashMap();
    List<CategoryNode> getCategoriesTree();
    List<Integer> getSubIdFromCategory(Integer navId, List<VproNavbar> list, List<Integer> idList);
    int addCategory(VproNavbar vproNavbar);
    int removeCategory(Integer id);
    int modifyCategory(VproNavbar vproNavbar);
    Map<Integer, List<Integer>> getSubIds();
    Map<Integer, List<Integer>> getSubIds(Integer navId) throws CategoryException;
}
