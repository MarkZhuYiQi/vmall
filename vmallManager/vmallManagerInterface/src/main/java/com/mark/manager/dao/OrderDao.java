package com.mark.manager.dao;

import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;

import java.util.List;

public interface OrderDao {
    String getNewOrderId();
    Boolean insertOrder(VproOrder vproOrder);
    Boolean insertOrderSub(List<VproOrderSub> subs);
}
