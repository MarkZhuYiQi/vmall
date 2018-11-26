package com.mark.manager.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class SessionFilter extends SecurityContextPersistenceFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        chain.doFilter(req, res);
    }
}
