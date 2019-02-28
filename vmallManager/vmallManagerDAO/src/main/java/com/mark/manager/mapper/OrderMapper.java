package com.mark.manager.mapper;

import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    List<Order> getOrderByCritria(@Param("orderCriteria") OrderCriteria orderCriteria);
}
