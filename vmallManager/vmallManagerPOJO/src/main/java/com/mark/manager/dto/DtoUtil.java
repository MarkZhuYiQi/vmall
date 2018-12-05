package com.mark.manager.dto;

import com.mark.common.pojo.CategoryNode;
import com.mark.common.pojo.User;
import com.mark.common.util.BeanUtil;
import com.mark.manager.pojo.VproCourses;
import com.mark.manager.pojo.VproCoursesContent;
import com.mark.manager.pojo.VproNavbar;

import java.beans.IntrospectionException;
import java.util.Map;

public class DtoUtil {
    public static User userRoles2user(UserRoles userRoles)
    {
        User user = new User();
        user.setAuthAppid(userRoles.getAuthAppid());
        user.setAuthAppkey(userRoles.getAuthAppkey());
        user.setAuthId(userRoles.getAuthAppid());
        user.setAuthIp(userRoles.getAuthIp());
        user.setAuthRolesId(userRoles.getRole().get(0).getRoleId());
        user.setAuthRolesName(userRoles.getRole().get(0).getRoleName());
        return user;
    }
    public static CategoryNode vproNavbar2CategoryNode(VproNavbar vproNavbar)
    {
        CategoryNode categoryNode = new CategoryNode();
        categoryNode.setNavId(vproNavbar.getNavId());
        categoryNode.setNavPid(vproNavbar.getNavPid());
        categoryNode.setNavIsParent(vproNavbar.getNavIsParent());
        categoryNode.setNavStatus(vproNavbar.getNavStatus());
        categoryNode.setNavNickname(vproNavbar.getNavNickname());
        categoryNode.setNavText(vproNavbar.getNavText());
        categoryNode.setNavUrl(vproNavbar.getNavUrl());
        return categoryNode;
    }

    /**
     * 传入的对象需要首先判断是否真的有值需要update
     * @param courseUpdate
     * @return
     */
    public static VproCourses courseUpdate2VproCoueses(CourseUpdate courseUpdate) {
        VproCourses vproCourses = new VproCourses();
        vproCourses.setCourseId(courseUpdate.getCourseId());
        if (courseUpdate.getCourseTitle() != null && !courseUpdate.getCourseTitle().equals("")) vproCourses.setCourseTitle(courseUpdate.getCourseTitle());
        if (courseUpdate.getCoursePrice() != null) vproCourses.setCoursePrice(courseUpdate.getCoursePrice());
        if (courseUpdate.getCourseStatus() != null) vproCourses.setCoursePrice(courseUpdate.getCoursePrice());
        if (courseUpdate.getCourseDiscountPrice() != null && !courseUpdate.getCourseDiscountPrice().toString().equals("-1")) vproCourses.setCoursePrice(courseUpdate.getCoursePrice());
        return vproCourses;
    }
    public static VproCoursesContent courseUpdate2CoursesContent(CourseUpdate courseUpdate) {
        VproCoursesContent vproCoursesContent = new VproCoursesContent();
        vproCoursesContent.setCourseContent(courseUpdate.getCourseContent().toString());
        return vproCoursesContent;
    }
}
