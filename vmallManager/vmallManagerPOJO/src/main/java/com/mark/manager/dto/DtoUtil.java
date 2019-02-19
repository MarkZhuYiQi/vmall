package com.mark.manager.dto;

import com.mark.common.pojo.CategoryNode;
import com.mark.common.pojo.User;
import com.mark.common.util.BeanUtil;
import com.mark.manager.pojo.VproCourses;
import com.mark.manager.pojo.VproCoursesContent;
import com.mark.manager.pojo.VproCoursesImage;
import com.mark.manager.pojo.VproNavbar;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.beans.IntrospectionException;
import java.math.BigDecimal;
import java.util.Map;

public class DtoUtil {
    public static User userRoles2user(UserRoles userRoles)
    {
        User user = new User();
        user.setAuthAppid(userRoles.getAuthAppid());
        user.setAuthAppkey(userRoles.getAuthAppkey());
        user.setAuthId(userRoles.getAuthId().toString());
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
        if (courseUpdate.getCourseCover() != null) vproCourses.setCourseCover(String.valueOf(courseUpdate.getCourseCover()));
        if (courseUpdate.getCourseDiscountPrice() != null && !courseUpdate.getCourseDiscountPrice().toString().equals("-1")) vproCourses.setCourseDiscountPrice(courseUpdate.getCourseDiscountPrice());
        return vproCourses;
    }
    public static VproCoursesContent courseUpdate2CoursesContent(CourseUpdate courseUpdate) {
        VproCoursesContent vproCoursesContent = new VproCoursesContent();
        vproCoursesContent.setCourseContent(courseUpdate.getCourseContent().toString());
        return vproCoursesContent;
    }
    public static VproCourses courses2VproCourses(Courses courses) {
        VproCourses vproCourses = new VproCourses();
        if (courses.getCoursePid() != null) vproCourses.setCoursePid(courses.getCoursePid());
        if (courses.getCourseTitle() != null) vproCourses.setCourseTitle(courses.getCourseTitle());
        if (courses.getCourseAuthor() != null) vproCourses.setCourseAuthor(courses.getCourseAuthor());
        if (courses.getCourseCover() != null) vproCourses.setCourseCover(courses.getCourseCover());
        if (courses.getCourseStatus() != null) vproCourses.setCourseStatus(Boolean.valueOf(String.valueOf(courses.getCourseStatus())));
        if (courses.getCoursePrice() != null) vproCourses.setCoursePrice(new BigDecimal(courses.getCoursePrice()));
        if (courses.getCourseDiscountPrice() != null) vproCourses.setCourseDiscountPrice(new BigDecimal(courses.getCourseDiscountPrice()));

        vproCourses.setCourseTime(String.valueOf(System.currentTimeMillis() / 1000));
        return vproCourses;
    }

    public static VproCoursesImage detailImage2VproCoursesImage(DetailImage detailImage) {
        VproCoursesImage vproCoursesImage = new VproCoursesImage();
        if (detailImage.getImageId() != null) vproCoursesImage.setImageId(detailImage.getImageId());
        if (detailImage.getImgUrl() != null) vproCoursesImage.setImageUrl(detailImage.getImgUrl());
        if (detailImage.getImageAuthor() != null) vproCoursesImage.setImageAuthor(detailImage.getImageAuthor());
        if (detailImage.getImageUsedLocation() != null) vproCoursesImage.setImageUsedLocation(String.valueOf(detailImage.getImageUsedLocation()));
        return vproCoursesImage;
    }
}
