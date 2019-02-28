package com.mark.manager.daoImpl;

import com.mark.common.exception.OrderException;
import com.mark.manager.dao.OrderDaoAbstract;
import com.mark.manager.dto.Order;
import com.mark.manager.mapper.OrderSubMapper;
import com.mark.manager.mapper.VproOrderMapper;
import com.mark.manager.mapper.VproOrderSubMapper;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("orderDB")
public class OrderDaoByDBImpl extends OrderDaoAbstract {
    @Autowired
    VproOrderMapper vproOrderMapper;
    @Autowired
    VproOrderSubMapper vproOrderSubMapper;
    @Autowired
    OrderSubMapper orderSubMapper;

    @Override
    public Boolean insertOrder(VproOrder vproOrder) throws OrderException {
        Integer res = vproOrderMapper.insertSelective(vproOrder);
        if (res <= 0) throw new OrderException("create order failed!" + vproOrder.toString());
        return res > 0;
    }

    @Override
    public Boolean insertOrderSub(List<VproOrderSub> subs) throws OrderException {
        Integer res = orderSubMapper.batchInsertSubOrder(subs);
        if (res <= 0) throw new OrderException("insert order sub infomation failed!" + subs.toString());
        return res > 0;
    }
}
