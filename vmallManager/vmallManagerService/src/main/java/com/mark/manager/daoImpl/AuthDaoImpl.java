package com.mark.manager.daoImpl;

import com.mark.common.constant.AuthConstant;
import com.mark.common.exception.AuthException;
import com.mark.manager.dao.AuthDao;
import com.mark.manager.dao.AuthDaoAbstract;
import com.mark.manager.pojo.VproAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("authDao")
public class AuthDaoImpl extends AuthDaoAbstract {

    @Autowired
    AuthDao authRedis;

    @Override
    public VproAuth getLoginInfo(String token) throws AuthException {
        try {
            return authRedis.getLoginInfo(token);
        } catch (AuthException a) {
            throw new AuthException(a.getMsg(), AuthConstant.LOGIN_INFO_NOT_EXIST);
        }

    }
}
