package com.mark.manager.dao;

import com.mark.common.exception.AuthException;
import com.mark.manager.pojo.VproAuth;

public abstract class AuthDaoAbstract implements AuthDao {
    @Override
    public VproAuth getLoginInfo(String token) throws AuthException {
        return null;
    }
}
