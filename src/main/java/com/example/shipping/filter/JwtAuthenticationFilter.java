package com.example.shipping.filter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.example.shipping.entity.LoginUser;
import com.example.shipping.utils.JwtUtil;
import com.example.shipping.utils.RedisCache;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return ;
        }
        //解析token
        String userId;
        try{
            Claims claim = JwtUtil.parseJWT(token);
            userId = claim.getSubject();
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("非法token");
        }
        //从redis中获取用户信息
        String redisKey = "Login:"+userId;
        LoginUser loginUser = JSON.parseObject(JSON.toJSONString(redisCache.getCacheObject(redisKey)), LoginUser.class);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        //存入SecurityContext
        //TODO 获取权限信息封装到AuthenticationToken中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
    
}
