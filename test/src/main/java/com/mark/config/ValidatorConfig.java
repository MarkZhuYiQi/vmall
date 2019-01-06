package com.mark.config;

import com.mark.pojo.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component
@Configuration
public class ValidatorConfig {
    @Bean(name = "localValidator")
    public Validator localValidator() {
        return new LocalValidatorFactoryBean();
    }
}
