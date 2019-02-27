package com.mark.manager.serviceImpl;

import com.mark.common.constant.OrderConstant;
import com.mark.common.exception.CartException;
import com.mark.common.exception.OrderException;
import com.mark.manager.dao.CartDao;
import com.mark.manager.dao.OrderDao;
import com.mark.manager.dto.Courses;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.PutOrder;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;
import com.mark.manager.service.CartService;
import com.mark.manager.service.CourseService;
import com.mark.manager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    @Qualifier("orderDao")
    OrderDao orderDao;

    @Autowired
    @Qualifier("cartDao")
    CartDao cartDao;

    @Autowired
    CartService cartService;

    @Override
    public Order checkOrder(PutOrder putOrder, Integer userId) throws OrderException {
        try {
            Order order = new Order();
            List<VproOrderSub> subs = new ArrayList<>();
            String cartId = cartDao.getCartIdByUserId(userId);
            if (!cartId.equals(putOrder.getCartId()))
                throw new OrderException("cartId does not fit with this user!", OrderConstant.CART_ID_NOT_FIT_WITH_USER_ID);
            String orderId = orderDao.getNewOrderId();
            order.setOrderId(orderId);
            // 判断优惠券
//            if (putOrder.getCouponId() != null)
            List<Courses> courses = cartService.getCourseDetailInCart(putOrder.getCoursesId());
            Double orderPrice = 0D;
            StringBuffer courseTitle = new StringBuffer();
            for (Courses c : courses) {
                VproOrderSub sub = new VproOrderSub();
                sub.setCourseId(Integer.parseInt(c.getCourseId()));
                sub.setCoursePrice(BigDecimal.valueOf(c.getCoursePrice()));
                sub.setOrderId(Long.parseLong(orderId));
                subs.add(sub);
                if (courseTitle.length() < 31) courseTitle.append(c.getCourseTitle());
                orderPrice = orderPrice + c.getCoursePrice();
            }
            order.setOrderTitle(courseTitle.substring(0, 31));
            if (Double.parseDouble(putOrder.getOrderPrice()) != orderPrice)
                throw new OrderException("orderPrice does not fit with the price calculated by back side.", OrderConstant.ORDER_PRICE_NOT_FIT_WITH_BACK_SIDE);
            order.setOrderPaymentPrice(String.valueOf(orderPrice));
            order.setOrderPayment(false);
            return order;
        } catch (CartException e) {
            throw new OrderException(e.getMsg(), e.getCode());
        }
    }

    @Override
    @Transactional
    public String putOrder(Order order, Integer userId) {
        VproOrder vproOrder = DtoUtil.Order2VproOrder(order);
        orderDao.insertOrder(vproOrder);
        orderDao.insertOrderSub(order.getVproOrderSubs());

    }

    @Override
    public Order getOrders() {
        return null;
    }
}
