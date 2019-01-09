package com.mark.manager.filter;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mark.common.jwt.JwtUtil;
import com.mark.manager.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class TokenFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthenticationFilter.class);
    private AuthService authService;
    public TokenFilter(AuthService authService)
    {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest)req).getHeader("MyToken");
        System.out.println(token);
        if (token == null)
        {
//            System.out.println("no token carried");
            chain.doFilter(req, res);
            return;
        }
        DecodedJWT jwt = JwtUtil.verifyToken(token);

        if (jwt != null) {
            logger.info("jwt验证通过");
            Map<String, Claim> claims = jwt.getClaims();
            for(Map.Entry<String, Claim> c : claims.entrySet())
            {
                System.out.println("key = " + c.getKey() + " and value = " + c.getValue());
            }
            String appId = claims.get("appid").asString();
            logger.info("jwt中的用户名获取：" + appId);
            String rolesName = "ROLE_STUDENT";
//            String rolesName = authService.getRoleByNameInRedis(appId);
            logger.info("拿到用户角色名：" + rolesName);
            // 这里的角色权限应该从数据库获得。
            // UsernamePasswordAuthenticationToken第一个参数是object，所以可以放对象的。
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(appId, "", AuthorityUtils.commaSeparatedStringToAuthorityList(rolesName)));
        }
        chain.doFilter(req, res);
    }
}
