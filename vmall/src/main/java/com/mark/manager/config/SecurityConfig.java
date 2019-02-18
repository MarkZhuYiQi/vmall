package com.mark.manager.config;

import com.mark.manager.component.AuthenticationPointExceptionHandler;
import com.mark.manager.component.AuthenticationUnAuthenticatedHandler;
import com.mark.manager.filter.CorsFilter;
import com.mark.manager.filter.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("begin security filter chain");
        System.out.print("begin security filter chain");
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .addFilterBefore(new CorsFilter(), JwtAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 设置认证失败处理器和拒绝访问处理器
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationPointExceptionHandler()).accessDeniedHandler(new AuthenticationUnAuthenticatedHandler());
    }
}
