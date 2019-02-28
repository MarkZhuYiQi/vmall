package com.mark.manager.daoImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mark.common.exception.OrderException;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.OrderDaoAbstract;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.mapper.OrderMapper;
import com.mark.manager.mapper.OrderSubMapper;
import com.mark.manager.mapper.VproOrderMapper;
import com.mark.manager.mapper.VproOrderSubMapper;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderExample;
import com.mark.manager.pojo.VproOrderSub;
import com.mark.manager.pojo.VproOrderSubExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("orderDB")
public class OrderDaoByDBImpl extends OrderDaoAbstract {
    @Autowired
    VproOrderMapper vproOrderMapper;
    @Autowired
    VproOrderSubMapper vproOrderSubMapper;
    @Autowired
    OrderSubMapper orderSubMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    JedisClient jedisClient;
    @Value("${orderPrefix}")
    String orderPrefix;
    @Value("${ordersBelongUserPrefix}")
    String ordersBelongUserPrefix;


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

    @Override
    public List<VproOrder> getOrdersByUserId(Integer userId) {
        VproOrderExample vproOrderExample = new VproOrderExample();
        vproOrderExample.createCriteria().andUserIdEqualTo(userId);
        return vproOrderMapper.selectByExample(vproOrderExample);
    }

    @Override
    public List<VproOrderSub> getExistCourseByUserOrder(List<Long> ordersId, List<Integer> coursesId) {
        VproOrderSubExample vproOrderSubExample = new VproOrderSubExample();
        vproOrderSubExample.createCriteria().andOrderIdIn(ordersId).andCourseIdIn(coursesId);
        return vproOrderSubMapper.selectByExample(vproOrderSubExample);
    }

    @Override
    public List<Order> getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException {
        if (orderCriteria.getUserId() == null) throw new OrderException("user id does not exist, could not procceed");
        PageHelper.startPage(orderCriteria.getpageNum(), orderCriteria.getPageSize());
        List<Order> orders = orderMapper.getOrderByCritria(orderCriteria);
        PageInfo page = new PageInfo(orders);
        return page.getList();
    }
}
