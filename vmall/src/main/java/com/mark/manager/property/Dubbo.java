package com.mark.manager.property;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:properties/dubbo.properties")
public class Dubbo {
}
