package com.example.shipping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * spring mvc 配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
    /**
     * 用此方法来添加视图控制器。
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //将根路径重定向到/login-view
        registry.addViewController("/").setViewName("login");
        //将login-view转到login页面
        registry.addViewController("/login-view").setViewName("login");

    }
}