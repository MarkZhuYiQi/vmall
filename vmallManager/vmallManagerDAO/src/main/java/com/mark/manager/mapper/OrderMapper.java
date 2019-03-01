package com.mark.manager.mapper;

import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> getOrderByCriteria(@Param("orderCriteria") OrderCriteria orderCriteria);
}
