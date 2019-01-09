package com.mark.manager.service;

import com.mark.manager.bo.Result;
import com.mark.manager.dto.Login;
import com.mark.manager.dto.UserRoles;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface AuthService {
    public Result genToken(Login login) throws Exception;
    public String verifyLogin(Login login);
    public UserRoles getAuthRoles(String appId);
    public Login decrypt(Login login) throws Exception;
    public String getRoleByNameInRedis(String appAuthId);
    public Map<String, String> getAuthByAuthIdFromRedis(String appAuthId);
}
