//package com.mark.manager.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class WebConfig implements WebMvcConfigurer {
//    {
//        System.out.println("CorsConfig");
//    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        System.out.println("addCors");
//        registry.addMapping("/auth")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "DELETE", "OPTIONS", "PUT")
//                .allowedHeaders("MyToken")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
//}