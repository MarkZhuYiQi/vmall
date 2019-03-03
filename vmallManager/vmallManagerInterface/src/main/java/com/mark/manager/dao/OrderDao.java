package com.mark.manager.dao;

import com.mark.common.exception.OrderException;
import com.mark.manager.bo.OrderResult;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.dto.OrderSub;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;

import java.util.List;

public interface OrderDao {
    String getNewOrderId();
    Boolean insertOrder(VproOrder vproOrder) throws OrderException;
    Boolean insertOrderSub(List<OrderSub> subs) throws OrderException;
    List<VproOrder> getOrdersByUserId(Integer userId);
    OrderResult getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException;
    List<VproOrderSub> getExistCourseByUserOrder(List<Long> ordersId, List<Integer> coursesId);
    Boolean setUserOrderCache(OrderResult orderResult, OrderCriteria orderCriteria) throws OrderException;
    Boolean checkCourseIfBought(String courseId, Integer userId) throws OrderException;
}
