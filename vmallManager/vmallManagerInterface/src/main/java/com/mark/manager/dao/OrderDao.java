package com.mark.manager.dao;

import com.github.pagehelper.PageInfo;
import com.mark.common.exception.OrderException;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;

import java.util.List;

public interface OrderDao {
    String getNewOrderId();
    Boolean insertOrder(VproOrder vproOrder) throws OrderException;
    Boolean insertOrderSub(List<VproOrderSub> subs) throws OrderException;
    List<VproOrder> getOrdersByUserId(Integer userId);
    List<Order> getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException;
    List<VproOrderSub> getExistCourseByUserOrder(List<Long> ordersId, List<Integer> coursesId);
    Boolean setUserOrderCache(List<Order> orders, OrderCriteria orderCriteria) throws OrderException;
}
