package com.mark.manager.dao;

import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;

import java.util.List;

public abstract class OrderDaoAbstract implements OrderDao {
    @Override
    public String getNewOrderId() {
        return null;
    }

    @Override
    public Boolean insertOrder(VproOrder vproOrder) {
        return null;
    }

    @Override
    public Boolean insertOrderSub(List<VproOrderSub> subs) {
        return null;
    }
}
