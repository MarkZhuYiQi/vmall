package com.mark.manager.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.manager.bo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登陆用户访问返回没有权限的提醒
 */
public class AuthenticationPointExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
/*
        PrintWriter out = httpServletResponse.getWriter();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"code\": 403, \"message\": \"unauthenticated token!\"}");
        out.write(sb.toString());
        out.flush();
        out.close();
*/
        Result result = new Result("unauthenticated token", 403);
        PrintWriter out = httpServletResponse.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        out.write(objectMapper.writeValueAsString(result));
        out.flush();
        out.close();
    }
}
