package com.mark.manager.service;

import com.mark.common.exception.OrderException;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.PutOrder;

public interface OrderService {
    Order checkOrder(PutOrder putOrder, Integer userId) throws OrderException;
    Order putOrder(Order order, Integer userId) throws OrderException;
    Order getOrders();
}
