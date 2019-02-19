package com.mark.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * 这个配置特么千万不能缺，这是让security接管filterChain的配置，不然security不起作用
 */
@Configuration
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
