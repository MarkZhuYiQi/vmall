package com.mark.manager.dao;

import com.mark.common.exception.PayException;
import com.mark.manager.pojo.VproOrder;

import java.util.Map;

public abstract class PayDaoAbstract implements PayDao {
    @Override
    public VproOrder updateOrderPayStatus(Map<String, String> params) throws PayException {
        return null;
    }
}
