package com.mark.manager.dao;

import com.mark.common.exception.OrderException;
import com.mark.manager.bo.OrderResult;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.dto.OrderSub;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderSub;

import java.util.List;

public abstract class OrderDaoAbstract implements OrderDao {
    @Override
    public String getNewOrderId() {
        return null;
    }

    @Override
    public Boolean insertOrder(VproOrder vproOrder) throws OrderException {
        return null;
    }

    @Override
    public Boolean insertOrderSub(List<OrderSub> subs) throws OrderException {
        return null;
    }

    @Override
    public List<VproOrderSub> getExistCourseByUserOrder(List<Long> ordersId, List<Integer> coursesId) {
        return null;
    }

    @Override
    public List<VproOrder> getOrdersByUserId(Integer userId) {
        return null;
    }

    @Override
    public OrderResult getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException {
        return null;
    }

    @Override
    public Boolean setUserOrderCache(OrderResult orderResult, OrderCriteria orderCriteria) throws OrderException {
        return null;
    }

    @Override
    public Boolean checkCourseIfBought(String courseId, Integer userId) throws OrderException {
        return null;
    }

    @Override
    public Order getOrderSpecified(Long orderId, Integer userId) throws OrderException {
        return null;
    }

    @Override
    public Boolean setOrderExpired(Long orderId, Integer userId) throws OrderException {
        return null;
    }

    @Override
    public void delUserOrderCache(String orderPayment, Integer userId) throws OrderException {
    }

    @Override
    public VproOrder getOrderByOrderId(Long orderId) {
        return null;
    }

    @Override
    public VproOrder updateOrderStatus(VproOrder vproOrder) {
        return null;
    }

    @Override
    public List<Long> getOrdersIdByCriteria(Integer userId, Integer orderPayment) {
        return null;
    }
}
