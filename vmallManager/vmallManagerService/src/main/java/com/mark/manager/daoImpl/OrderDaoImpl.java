package com.mark.manager.daoImpl;

import com.mark.manager.dao.OrderDao;
import com.mark.manager.dao.OrderDaoAbstract;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("orderDao")
public class OrderDaoImpl extends OrderDaoAbstract {
    @Autowired
    @Qualifier("orderDB")
    OrderDao orderDaoByDB;

    @Autowired
    @Qualifier("orderRedis")
    OrderDao orderDaoByRedis;

    @Override
    public String getNewOrderId() {
        return orderDaoByRedis.getNewOrderId();
    }

    @Override
    public Boolean insertOrder(VproOrder vproOrder) {
        return orderDaoByDB.insertOrder(vproOrder);
    }

    @Override
    public Boolean insertOrderSub(List<VproOrderSub> subs) {
        return orderDaoByDB.insertOrderSub(subs);
    }
}
