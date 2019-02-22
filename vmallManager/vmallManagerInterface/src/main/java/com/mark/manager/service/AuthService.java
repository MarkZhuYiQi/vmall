package com.mark.manager.service;

import com.mark.manager.bo.Result;
import com.mark.manager.dto.Login;
import com.mark.manager.dto.UserRoles;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface AuthService {
    String encryptForTest(String str) throws Exception;
    Result genToken(Login login, String ip) throws Exception;
    String verifyLogin(Login login);
    UserRoles getAuthRoles(String appId);
    Login decrypt(Login login) throws Exception;
    String getRoleByNameInRedis(String appAuthId);
    Map<String, String> getAuthByAuthIdFromRedis(String appAuthId);
}
