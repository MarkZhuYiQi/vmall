package com.mark.manager.serviceImpl;

import com.mark.common.constant.OrderConstant;
import com.mark.common.exception.CartException;
import com.mark.common.exception.CourseException;
import com.mark.common.exception.OrderException;
import com.mark.manager.bo.OrderResult;
import com.mark.manager.dao.CartDao;
import com.mark.manager.dao.OrderDao;
import com.mark.manager.dto.*;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;
import com.mark.manager.service.CartService;
import com.mark.manager.service.CourseService;
import com.mark.manager.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    @Qualifier("orderDao")
    OrderDao orderDao;

    @Autowired
    @Qualifier("cartDao")
    CartDao cartDao;

    @Autowired
    CartService cartService;

    @Autowired
    CourseService courseService;


    /**
     * 检查order信息是否正确，正确返回控制器准备创建订单
     * @param putOrder
     * @param userId
     * @return
     * @throws OrderException
     */
    @Override
    public Order checkOrder(PutOrder putOrder, Integer userId) throws OrderException {
        try {
            Order order = new Order();
            List<OrderSub> subs = new ArrayList<>();
            String cartId = cartDao.getCartIdByUserId(userId);
            if (!cartId.equals(putOrder.getCartId()))
                throw new OrderException("cartId does not fit with this user!", OrderConstant.CART_ID_NOT_FIT_WITH_USER_ID);
            String orderId = orderDao.getNewOrderId();
            order.setOrderId(orderId);
            // 判断优惠券
//            if (putOrder.getCouponId() != null)
            List<Courses> courses = cartService.getCourseDetailInCart(putOrder.getCoursesId());
            BigDecimal orderPrice = new BigDecimal("0");
            StringBuffer courseTitle = new StringBuffer();
            for (Courses c : courses) {
                OrderSub sub = new OrderSub();
                sub.setCourseId(Integer.parseInt(c.getCourseId()));
                sub.setCoursePrice(c.getCoursePrice());
                sub.setOrderId(Long.parseLong(orderId));
                sub.setUserId(userId);
                subs.add(sub);
                if (courseTitle.length() < 31) courseTitle.append(c.getCourseTitle());
                orderPrice = orderPrice.add(c.getCoursePrice());
            }
            order.setOrderSubs(subs);
            logger.info("orderPrice： " + orderPrice);
            logger.info("front orderPrice： " + Double.parseDouble(putOrder.getOrderPrice()));
            logger.info(String.valueOf(new BigDecimal(putOrder.getOrderPrice()).equals(orderPrice)));

            order.setOrderTitle(courseTitle.length() > 31 ? courseTitle.substring(0, 31) : courseTitle.toString());
            if (new BigDecimal(putOrder.getOrderPrice()).equals(orderPrice))
                throw new OrderException("orderPrice does not fit with the price calculated by back side.", OrderConstant.ORDER_PRICE_NOT_FIT_WITH_BACK_SIDE);
            order.setOrderPaymentPrice(String.valueOf(orderPrice));
            order.setOrderPayment(0);
            order.setUserId(userId);
            return order;
        } catch (CartException e) {
            throw new OrderException(e.getMsg(), e.getCode());
        }
    }

    /**
     * 检查课程是否已经购买
     * @param coursesId
     * @param userId
     * @return
     */
    @Override
    public List<Long> checkCourseIfExisted(List<String> coursesId, Integer userId) {
        List<VproOrder> orders = orderDao.getOrdersByUserId(userId);
        // 所有订单号
        List<Long>  ordersId = new ArrayList<>();
        for (VproOrder v : orders) {
            ordersId.add(v.getOrderId());
        }
        List<Integer> ids = new ArrayList<>();
        for (String c : coursesId) {
            ids.add(Integer.parseInt(c));
        }
        List<VproOrderSub> orderSubs = orderDao.getExistCourseByUserOrder(ordersId, ids);
        List<Long> exists = new ArrayList<>();
        if (orderSubs.size() > 0) {
            for (VproOrderSub sub : orderSubs) {
                exists.add(sub.getOrderId());
            }
        }
        return exists;
    }

    /**
     * 创建订单
     * @param order
     * @param userId
     * @return
     * @throws OrderException
     */
    @Override
    @Transactional
    public Order putOrder(Order order, Integer userId) throws OrderException {
        try {
            VproOrder vproOrder = DtoUtil.Order2VproOrder(order);
            String cartId = cartDao.getCartIdByUserId(userId);
            // 插入主订单
            orderDao.insertOrder(vproOrder);
            // 插入子订单
            orderDao.insertOrderSub(order.getOrderSubs());
            // 删除购物车元素
            for (OrderSub sub : order.getOrderSubs()) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCartCourseId(sub.getCourseId().longValue());
                cartDetail.setCartIsCookie(false);
                cartDetail.setCartParentId(Long.parseLong(cartId));
                cartDao.delCartItem(cartDetail);
            }
            return order;
        } catch (OrderException oe) {
            throw new OrderException(oe.getMsg(), oe.getCode());
        } catch (CartException e) {
            throw new OrderException(e.getMsg(), e.getCode());
        }
    }

    /**
     * 根据条件显示用户的订单
     * @param orderCriteria
     * @return
     * @throws OrderException
     */
    @Override
    public OrderResult getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException {
        try {
            return orderDao.getOrdersByCriteria(orderCriteria);
        } catch (OrderException e) {
            throw new OrderException(e.getMsg(), e.getCode());
        }
    }
    /**
     * 检查课程是否已经购买
     * @param courseId
     * @param userId
     * @return
     * @throws OrderException
     */
    @Override
    public Boolean checkCourseIfBought(String courseId, Integer userId) throws OrderException {
        try {
            return orderDao.checkCourseIfBought(courseId, userId);
        } catch (OrderException e) {
            throw new OrderException(e.getMsg(), e.getCode());
        }
    }

    @Override
    public Order getOrderSpecified(Long orderId, Integer userId) throws OrderException {
        return orderDao.getOrderSpecified(orderId, userId);
    }

    @Override
    public Boolean setOrderExpired(Long orderId, Integer userId) throws OrderException {
        return orderDao.setOrderExpired(orderId, userId);
    }
    @Override
    public List<Courses> getCoursesBoughtByUser(Integer userId) throws OrderException, CourseException {
        List<Courses> courses = new ArrayList<>();
        List<Long> list = orderDao.getOrdersIdByCriteria(userId, 1);
        Set<String> coursesId = new HashSet<>();
        for (Long orderId : list) {
            Order order = orderDao.getOrderSpecified(orderId, userId);
            for (OrderSub orderSub : order.getOrderSubs()) {
                coursesId.add(String.valueOf(orderSub.getCourseId()));
            }
        }
        for (String courseId : coursesId) {
            courses.add(courseService.getCourseForDetail(Integer.parseInt(courseId)));
        }
        return courses;
    }
}
