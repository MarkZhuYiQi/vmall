package com.mark.manager.daoImpl;

import com.mark.common.constant.OrderConstant;
import com.mark.common.exception.CourseException;
import com.mark.common.exception.OrderException;
import com.mark.manager.bo.OrderResult;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dao.OrderDao;
import com.mark.manager.dao.OrderDaoAbstract;
import com.mark.manager.dto.Courses;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.dto.OrderSub;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mark
 * 目前存在问题：order列表目录需要重新规划，目前使用sorted set，score为页码，member为整页缓存
 * 在状态更新后需要整页重新生成，效率极低
 */
@Component("orderDao")
public class OrderDaoImpl extends OrderDaoAbstract {
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Autowired
    @Qualifier("orderDB")
    OrderDao orderDaoByDB;

    @Autowired
    @Qualifier("orderRedis")
    OrderDao orderDaoByRedis;

    @Autowired
    @Qualifier("courseDao")
    CourseDao courseDao;

    @Override
    public String getNewOrderId() {
        return orderDaoByRedis.getNewOrderId();
    }

    @Override
    public Boolean insertOrder(VproOrder vproOrder) throws OrderException {
        try {
            return orderDaoByDB.insertOrder(vproOrder);
        } catch (OrderException e) {
            logger.error(e.getMsg());
            throw new OrderException(e.getMsg(), OrderConstant.ORDER_CREATE_FAILED);
        }
    }

    @Override
    public Boolean insertOrderSub(List<OrderSub> subs) throws OrderException {
        try {
            return orderDaoByDB.insertOrderSub(subs);
        } catch (OrderException e) {
            logger.error(e.getMsg());
            throw new OrderException(e.getMsg(), OrderConstant.ORDER_CREATE_FAILED);
        }
    }

    /**
     * 根据用户id获得其所有订单
     * @param userId
     * @return
     */
    @Override
    public List<VproOrder> getOrdersByUserId(Integer userId) {
        return orderDaoByDB.getOrdersByUserId(userId);
    }

    /**
     *
     * @param ordersId
     * @param coursesId
     * @return
     */
    @Override
    public List<VproOrderSub> getExistCourseByUserOrder(List<Long> ordersId, List<Integer> coursesId) {
        return orderDaoByDB.getExistCourseByUserOrder(ordersId, coursesId);
    }

    /**
     * 检查课程是否已经购买（废话当然从订单里看啦）
     * @param courseId
     * @param userId
     * @return
     * @throws OrderException
     */
    @Override
    public Boolean checkCourseIfBought(String courseId, Integer userId) throws OrderException {
//        try {
//            return orderDaoByRedis.checkCourseIfBought(courseId, userId);
//        } catch (OrderException oe) {
            try {
                return orderDaoByDB.checkCourseIfBought(courseId, userId);
            } catch (OrderException e) {
                throw new OrderException(e.getMsg(), OrderConstant.COURSE_CHECK_IF_BOUGHT_FAILED);
            }
//        }
    }

    /**
     * 用户订单页面显示订单量，根据条件获得符合的所有订单
     * @param orderCriteria
     * @return
     * @throws OrderException
     */
    @Override
    public OrderResult getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException {
        try {
            return orderDaoByRedis.getOrdersByCriteria(orderCriteria);
        } catch (OrderException e) {
            OrderResult orderResult = new OrderResult();
            try {
                orderResult = orderDaoByDB.getOrdersByCriteria(orderCriteria);
                for (Order r : orderResult.getOrders()) {
                    for (OrderSub os : r.getOrderSubs()) {
                        Courses course = courseDao.getCourseForDetail(os.getCourseId());
                        os.setCourseTitle(course.getCourseTitle());
                        os.setCourseAuthor(course.getCourseAuthor());
                        os.setCourseCover(course.getVproCoursesCover().getCourseCoverKey());
                    }
                }
                orderDaoByRedis.setUserOrderCache(orderResult, orderCriteria);
                return orderResult;
            } catch (OrderException oe) {
                throw new OrderException(oe.getMsg(), OrderConstant.GET_ORDER_FAILED);
            } catch (CourseException ce) {
                throw new OrderException(ce.getMsg(), ce.getCode());
            }
        }
    }

    @Override
    public Order getOrderSpecified(Long orderId, Integer userId) throws OrderException {
        try {
            return orderDaoByDB.getOrderSpecified(orderId, userId);
        } catch (OrderException e) {
            logger.warn("{}, orderId: {}, userId: {}", e.getMsg(), orderId, userId);
            throw new OrderException(e.getMsg(), OrderConstant.ORDER_NOT_FOUND);
        }
    }

    @Override
    public Boolean setOrderExpired(Long orderId, Integer userId) throws OrderException {
        try {
            Boolean res = orderDaoByDB.setOrderExpired(orderId, userId);
            delUserOrderCache("0", userId);
            delUserOrderCache("2", userId);
            return res;
        } catch (OrderException e) {
            logger.warn("{}, orderId: {}, userId: {}", e.getMsg(), orderId, userId);
            throw new OrderException(e.getMsg(), OrderConstant.SET_ORDER_EXPIRED_FAILED);
        }
    }

    @Override
    public void delUserOrderCache(String orderPayment, Integer userId) throws OrderException {
        orderDaoByRedis.delUserOrderCache(orderPayment, userId);
    }

    @Override
    public VproOrder getOrderByOrderId(Long orderId) {
        return orderDaoByDB.getOrderByOrderId(orderId);
    }

    /**
     * 这里需要同时去更新redis状态
     * @param vproOrder
     * @return
     */
    @Override
    public VproOrder updateOrderStatus(VproOrder vproOrder) {
        return orderDaoByDB.updateOrderStatus(vproOrder);
    }
}
