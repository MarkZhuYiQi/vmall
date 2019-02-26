package com.mark.manager.dao;

import com.mark.common.exception.AuthException;
import com.mark.manager.pojo.VproAuth;

public interface AuthDao {
    VproAuth getLoginInfo(String token) throws AuthException;
}
