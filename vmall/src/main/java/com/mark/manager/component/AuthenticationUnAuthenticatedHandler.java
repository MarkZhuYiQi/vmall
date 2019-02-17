package com.mark.manager.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.manager.bo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationUnAuthenticatedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        Result result = new Result("You do not have permission to access this function!", 403);
        ObjectMapper objectMapper = new ObjectMapper();
        printWriter.write(objectMapper.writeValueAsString(result));
        printWriter.flush();
        printWriter.close();
    }
}
