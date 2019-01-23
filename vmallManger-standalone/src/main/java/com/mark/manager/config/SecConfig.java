package com.mark.manager.config;

import com.mark.manager.filter.JwtAuthenticationFilter;
import com.mark.manager.filter.SessionFilter;
import com.mark.manager.filter.TokenFilter;
import com.mark.manager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthService authService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/index", "/auth/**", "/test/**").permitAll()
//                .antMatchers("/category/tree").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                // 清除session状态
                .addFilterBefore(new SessionFilter(), TokenFilter.class)
//                 对登录信息进行验证
                .addFilterBefore(new TokenFilter(authService), UsernamePasswordAuthenticationFilter.class)
                // 检测token状态 暂时无用
//                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;

        // session策略失效
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // session策略失效
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 认证失败的处理方法
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setStatus(9999);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setHeader("myError", "badtoken");
                httpServletRequest.getRequestDispatcher("/badToken").forward(httpServletRequest, httpServletResponse);
            }
        });
    }
}
