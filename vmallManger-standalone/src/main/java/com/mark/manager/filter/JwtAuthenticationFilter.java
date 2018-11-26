package com.mark.manager.filter;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mark.common.jedis.JedisClientPool;
import com.mark.common.jwt.JwtUtil;
import com.mark.manager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class JwtAuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest)servletRequest).getHeader("MyToken");
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        if (jwt != null) {
            Map<String, Claim> claims = jwt.getClaims();
            String appId = claims.get("appid").asString();


//            SecurityContextHolder.getContext().setAuthentication(
//                    new UsernamePasswordAuthenticationToken(appId, "", AuthorityUtils.commaSeparatedStringToAuthorityList("guest, admin")));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
