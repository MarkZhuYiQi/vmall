package com.mark.manager.service;

import com.mark.common.exception.OrderException;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.dto.PutOrder;

import java.util.List;

public interface OrderService {
    Order checkOrder(PutOrder putOrder, Integer userId) throws OrderException;
    List<Long> checkCourseIfExisted(List<String> coursesId, Integer userId) throws OrderException;
    Order putOrder(Order order, Integer userId) throws OrderException;
    List<Order> getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException;
}
