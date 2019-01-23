package com.mark.manager.service;

import com.mark.common.pojo.CategoryNode;
import com.mark.manager.bo.Result;
import com.mark.manager.pojo.VproNavbar;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    public List<VproNavbar> getCategories();
    public Map<Integer, VproNavbar> getCategoriesAsHashMap();
    public List<CategoryNode> getCategoriesTree();
    public List<Integer> getSubIdFromCategory(Integer navId, List<VproNavbar> list, List<Integer> idList);
    public int addCategory(VproNavbar vproNavbar);
    public int removeCategory(Integer id);
    public int modifyCategory(VproNavbar vproNavbar);
}
