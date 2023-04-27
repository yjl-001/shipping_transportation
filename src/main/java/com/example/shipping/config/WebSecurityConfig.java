package com.example.shipping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.shipping.filter.JwtAuthenticationFilter;
/**
 * spring security配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig{
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
        //设置过滤器
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        //设置请求认证
        .authorizeHttpRequests()
            .requestMatchers("/css/**").permitAll()
            .requestMatchers("/js/**").permitAll()
            // .requestMatchers("/**").permitAll()
            .requestMatchers("/login-view").anonymous()
            .requestMatchers("/user/login").anonymous()
            .requestMatchers("/register-view").anonymous()
            .requestMatchers("/user/register").anonymous()
            //其它的请求都需要登录后才能访问
            .anyRequest().authenticated()
        .and()
        //允许表单登录
        .formLogin()
            //登录页面路径
            .loginPage("/login-view")
            .loginProcessingUrl("/login")
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .logout()
            //登录退出
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login-view?logout")
        .and().build();

    }
}
