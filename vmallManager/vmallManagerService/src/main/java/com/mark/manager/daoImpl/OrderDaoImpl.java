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

    @Override
    public List<VproOrder> getOrdersByUserId(Integer userId) {
        return orderDaoByDB.getOrdersByUserId(userId);
    }

    @Override
    public List<VproOrderSub> getExistCourseByUserOrder(List<Long> ordersId, List<Integer> coursesId) {
        return orderDaoByDB.getExistCourseByUserOrder(ordersId, coursesId);
    }

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
}
