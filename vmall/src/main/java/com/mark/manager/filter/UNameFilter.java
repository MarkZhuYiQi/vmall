package com.mark.manager.filter;

import com.mark.manager.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class UNameFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    AuthService authService;

    private Logger logger = LoggerFactory.getLogger(UNameFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.info("UNameFilter");
        SecurityContextHolder.clearContext();
        Object tokenObj = req.getParameter("token");

        if (tokenObj == null) {
            chain.doFilter(req, res);
            return;
        }
        String token = (String)tokenObj;
        System.out.println(token);
        // token有而且认证成功
        // 设置认证信息，SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("mark", "", AuthorityUtils.createAuthorityList("ROLE_" + token )));
        chain.doFilter(req, res);
    }
}
