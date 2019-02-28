package com.mark.manager.dao;

import com.mark.common.exception.OrderException;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;

import java.util.List;

public interface OrderDao {
    String getNewOrderId();
    Boolean insertOrder(VproOrder vproOrder) throws OrderException;
    Boolean insertOrderSub(List<VproOrderSub> subs) throws OrderException;
}
