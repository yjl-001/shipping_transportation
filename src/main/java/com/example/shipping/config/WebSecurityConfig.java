package com.example.shipping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
/**
 * spring security配置
 */
@Configuration
public class WebSecurityConfig{
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/r/**").authenticated()
            //除了/r/**，其它的请求可以访问
            .anyRequest().permitAll()
            .and()
            //允许表单登录
            .formLogin()
            //登录页面路径
            .loginPage("/login-view")
            .loginProcessingUrl("/login")
            //自定义登录成功的页面地址
            .successForwardUrl("/index")
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            .logout()
            //登录退出
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login-view?logout")
            .and().build();

    }
}
