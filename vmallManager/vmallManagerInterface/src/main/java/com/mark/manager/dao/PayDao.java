package com.mark.manager.dao;

import com.mark.common.exception.PayException;
import com.mark.manager.pojo.VproOrder;

import java.util.Map;

public interface PayDao {
    VproOrder updateOrderPayStatus(Map<String, String> params) throws PayException;
}
