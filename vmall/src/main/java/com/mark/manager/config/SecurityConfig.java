package com.mark.manager.config;

import com.mark.manager.component.AuthenticationPointExceptionHandler;
import com.mark.manager.component.AuthenticationUnAuthenticatedHandler;
import com.mark.manager.filter.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("begin security filter chain");
        System.out.println("begin security filter chain");
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        http
                .addFilterBefore(characterEncodingFilter, CsrfFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET, "/login").authenticated()
                .antMatchers(HttpMethod.GET, "/cart/detail").authenticated()
                .antMatchers(HttpMethod.POST, "/course/check").authenticated()
                .antMatchers(HttpMethod.POST, "/comment").authenticated()
                .antMatchers("/order/**").authenticated()
//                .antMatchers("/pay/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 设置认证失败处理器和拒绝访问处理器
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationPointExceptionHandler()).accessDeniedHandler(new AuthenticationUnAuthenticatedHandler());
        logger.info("filter chain set finished");
        System.out.println("filter chain set finished");
    }
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }
}
