package com.oj.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Title: http设置
 * @Description: TODO
 * @Author: zhenyu
 * @DateTime: 2022/8/28 17:26
 */

@Configuration
public class webConfig implements WebMvcConfigurer {
    //解决跨域问题
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
