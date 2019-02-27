package com.mark.manager.daoImpl;

import com.mark.manager.dao.OrderDao;
import com.mark.manager.dao.OrderDaoAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("orderDao")
public class OrderDaoImpl extends OrderDaoAbstract {
    @Autowired
    @Qualifier("orderDB")
    OrderDao orderDaoByDB;

    @Autowired
    @Qualifier("orderRedis")
    OrderDao orderDaoByRedis;
}
