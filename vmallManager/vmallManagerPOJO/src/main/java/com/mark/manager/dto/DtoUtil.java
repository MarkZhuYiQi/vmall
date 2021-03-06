package com.mark.manager.dto;

import com.mark.common.pojo.CategoryNode;
import com.mark.common.pojo.User;
import com.mark.common.util.BeanUtil;
import com.mark.manager.pojo.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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
        if (courses.getCoursePrice() != null) vproCourses.setCoursePrice(courses.getCoursePrice());
        if (courses.getCourseDiscountPrice() != null) vproCourses.setCourseDiscountPrice(courses.getCourseDiscountPrice());

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
    public static VproCartDetail cartDetail2VproCartDetail(CartDetail cartDetail) {
        VproCartDetail vproCartDetail = new VproCartDetail();
        vproCartDetail.setCartIsCookie(cartDetail.getCartIsCookie());
        if (cartDetail.getCartAddTime() != null) vproCartDetail.setCartAddTime(cartDetail.getCartAddTime());
        if (cartDetail.getCartCourseId() != null) vproCartDetail.setCartCourseId(String.valueOf(cartDetail.getCartCourseId()));
        if (cartDetail.getCartParentId() != null) vproCartDetail.setCartParentId(cartDetail.getCartParentId());
        return vproCartDetail;
    }
    public static Cart VproCart2Cart(VproCart vproCart, List<VproCartDetail> list) {
        Cart cart = new Cart();
        if (vproCart.getCartId() != null) cart.setCartId(vproCart.getCartId());
        if (vproCart.getCartAddtime() != null) cart.setCartAddTime(Long.parseLong(vproCart.getCartAddtime()));
        if (vproCart.getCartUserid() != null) cart.setCartUserId(vproCart.getCartUserid());
        if (vproCart.getCartPayment() != null) cart.setCartPayment(Boolean.parseBoolean(vproCart.getCartPayment()));
        if (vproCart.getCartStatus() != null) cart.setCartStatus(Boolean.parseBoolean(vproCart.getCartStatus()));
        if (list != null && list.size() > 0) cart.setCartDetail(list);
        return cart;
    }
    public static VproOrder Order2VproOrder(Order order) {
        VproOrder vproOrder = new VproOrder();
        vproOrder.setOrderId(Long.parseLong(order.getOrderId()));
        if (order.getOrderCouponUsed() == null) {
            vproOrder.setOrderCouponUsed(0);
        } else {
            vproOrder.setOrderCouponUsed(order.getOrderCouponUsed() ? 1 : 0);
        }
        if (order.getOrderDiscount() == null) {
            vproOrder.setOrderDiscount(0);
        } else {
            vproOrder.setOrderDiscount(Integer.parseInt(order.getOrderDiscount()));
        }
        if (order.getOrderCouponUsed() == null) {
            vproOrder.setOrderPayment(0);
        } else {
            vproOrder.setOrderPayment(order.getOrderPayment());
        }
        if (order.getOrderPaymentId() == null) {
            vproOrder.setOrderPaymentId(null);
        } else {
            vproOrder.setOrderPaymentId(order.getOrderPaymentId());
        }
        vproOrder.setOrderPaymentPrice(new BigDecimal(0));
        vproOrder.setOrderPrice(new BigDecimal(order.getOrderPaymentPrice()));
        vproOrder.setOrderTitle(order.getOrderTitle());
        vproOrder.setOrderTime(String.valueOf(System.currentTimeMillis() / 1000));
        vproOrder.setUserId(order.getUserId());
        return vproOrder;
    }
    public static Order vproOrder2Order(VproOrder vproOrder) {
        Order order = new Order();
        order.setUserId(vproOrder.getUserId());
        order.setOrderId(String.valueOf(vproOrder.getOrderId()));
        order.setOrderPayment(vproOrder.getOrderPayment());
        order.setOrderPaymentPrice(String.valueOf(vproOrder.getOrderPaymentPrice()));
        order.setOrderTitle(vproOrder.getOrderTitle());
        order.setOrderCouponUsed(vproOrder.getOrderCouponUsed() == 1);
        order.setOrderDiscount(String.valueOf(vproOrder.getOrderDiscount()));
        order.setOrderPaymentId(vproOrder.getOrderPaymentId());
        order.setOrderTime(vproOrder.getOrderTime());
        order.setOrderPrice(vproOrder.getOrderPrice().toString());
        return order;
    }
    public static OrderSub vproOrderSub2OrderSub(VproOrderSub vproOrderSub) {
        OrderSub orderSub = new OrderSub();
        orderSub.setOrderId(vproOrderSub.getOrderId());
        orderSub.setOrderSubId(vproOrderSub.getOrderSubId());
        orderSub.setCourseId(vproOrderSub.getCourseId());
        orderSub.setCoursePrice(vproOrderSub.getCoursePrice());
        return orderSub;
    }
    public static Comment vproComment2Comment(VproComment vproComment) {
        Comment comment = new Comment();
        comment.setVproCommentId(vproComment.getVproCommentId());
        comment.setVproCommentContent(vproComment.getVproCommentContent());
        comment.setVproCommentCourseId(vproComment.getVproCommentCourseId());
        comment.setVproCommentLessonId(vproComment.getVproCommentLessonId());
        comment.setVproCommentReplyMainId(vproComment.getVproCommentReplyMainId());
        comment.setVproCommentReplyId(vproComment.getVproCommentReplyId());
        comment.setVproCommentIsPublished(vproComment.getVproCommentIsPublished());
        comment.setVproCommentTime(vproComment.getVproCommentTime());
        comment.setVproCommentUserId(vproComment.getVproCommentUserId());
        return comment;
    }
    public static VproComment comment2VproComment(Comment comment) {
        VproComment vproComment = new VproComment();
        vproComment.setVproCommentId(comment.getVproCommentId());
        vproComment.setVproCommentContent(comment.getVproCommentContent());
        vproComment.setVproCommentCourseId(comment.getVproCommentCourseId());
        vproComment.setVproCommentLessonId(comment.getVproCommentLessonId());
        vproComment.setVproCommentReplyMainId(comment.getVproCommentReplyMainId());
        vproComment.setVproCommentReplyId(comment.getVproCommentReplyId());
        vproComment.setVproCommentIsPublished(comment.getVproCommentIsPublished());
        vproComment.setVproCommentTime(comment.getVproCommentTime());
        vproComment.setVproCommentUserId(comment.getVproCommentUserId());
        return vproComment;
    }
    public static Map<String, String> order2Map(Order order) {
        Map<String, String> hash = new HashMap<String, String>();
        hash.put("orderId", order.getOrderId());
        hash.put("orderPaymentId", order.getOrderPaymentId() == null ? "" : order.getOrderPaymentId());
        hash.put("orderPayment", String.valueOf(order.getOrderPayment()));
        hash.put("orderDiscount", order.getOrderDiscount());
        hash.put("orderCouponUsed", order.getOrderCouponUsed() ? "1" : "0");
        hash.put("orderPaymentPrice", order.getOrderPaymentPrice());
        hash.put("orderPrice", order.getOrderPrice());
        hash.put("orderSubs", BeanUtil.parseObjToJson(order.getOrderSubs()));
        hash.put("orderTime", order.getOrderTime());
        hash.put("userId", String.valueOf(order.getUserId()));
        hash.put("orderTitle", order.getOrderTitle());
        return hash;
    }
    public static Order map2Order(Map<String, String> hash) {
        Order order = new Order();
        order.setOrderId(hash.get("orderId"));
        order.setOrderPaymentId(hash.get("orderPaymentId"));
        order.setOrderPayment(Integer.parseInt(hash.get("orderPayment")));
        order.setOrderDiscount(hash.get("orderDiscount"));
        order.setOrderCouponUsed(hash.get("orderCouponUsed").equals("1"));
        order.setOrderPaymentPrice(hash.get("orderPaymentPrice"));
        order.setOrderPrice(hash.get("orderPrice"));
        order.setOrderSubs(BeanUtil.parseObjToList(hash.get("orderSubs"), OrderSub.class));
        order.setOrderTitle(hash.get("orderTitle"));
        order.setUserId(Integer.parseInt(hash.get("userId")));
        order.setOrderTime(hash.get("orderTime"));
        return order;
    }
}
