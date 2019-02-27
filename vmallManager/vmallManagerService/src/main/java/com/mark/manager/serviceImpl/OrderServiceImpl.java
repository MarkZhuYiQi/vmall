package com.mark.manager.serviceImpl;

import com.mark.manager.dao.OrderDao;
import com.mark.manager.dto.Order;
import com.mark.manager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    @Qualifier("orderDao")
    OrderDao orderDao;

    @Override
    public Order putOrder() {

    }

    @Override
    public Order getOrders() {

    }
}
