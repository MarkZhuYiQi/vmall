package com.mark.manager.service;

import com.mark.manager.bo.Result;
import com.mark.manager.dto.Login;
import com.mark.manager.dto.UserRoles;

import java.io.UnsupportedEncodingException;

public interface AuthService {
    public Result genToken(Login login);
    public String verifyLogin(Login login);
    public UserRoles getAuthRoles(String appId);
    public Login decrypt(Login login);
    public String getRoleByNameInRedis(String appAuthId);
}
