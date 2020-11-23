package com.tiandi.logistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS跨域资源共享配置类
 * 用于对跨域请求支持
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/23 15:30
 */
@Component
public class CORSConfig {
    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                        .maxAge(3600);
            }

        };
    }
}
