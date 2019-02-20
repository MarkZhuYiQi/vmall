package com.mark.manager.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.mark.common.jwt.JwtUtil;
import com.mark.common.pojo.JwtUserDetails;
import com.mark.common.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * 通过token还原用户信息
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    @Autowired
//    PropertyConfig propertyConfig;
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        logger.warn("JwtAuthenticationFilter");
        logger.warn("filter: JwtAuthenticationFilter");
        String tokenHeader = "MyToken";
        String authToken = httpServletRequest.getHeader(tokenHeader);
        final String authTokenStart = "";
        System.out.println("Token:" + authToken);
        if (!StringUtils.isEmpty(authToken) && authToken.startsWith(authTokenStart)) {
            authToken = authToken.substring(authTokenStart.length());
            // 测试是否有效
            DecodedJWT jwt = JwtUtil.verifyToken(authToken);
//            Map<String, Claim> claims = jwt.getClaims();
            String username = jwt.getClaim("appid").asString();
            System.out.println("getClaim: " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 用户数据可以从数据库获取或者从token中获取
                // It is not compelling necessary to load the use details from the database. You could also store the information
                // in the token and read it from it. It's up to you ;)
                // UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                JwtUserDetails userDetails = JwtUtil.getUserFromToken(authToken);
                System.out.println("getLocalAddr: " + IpUtil.getIpAddress(httpServletRequest));
                System.out.println(userDetails.toString());
                // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
                // the database compellingly. Again it's up to you ;)
                if (JwtUtil.verifyToken(authToken) != null) {
                    System.out.println("Gen authentication");
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
                    // 生成登陆的额外信息
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    logger.info(String.format("Authenticated user %s, setting security context", username));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("当前身份：" + SecurityContextHolder.getContext().getAuthentication());
                }
            }
        } else {
            // 不符合规范，不通过验证
            System.out.println("token = null, authentication missed");
            authToken = null;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
