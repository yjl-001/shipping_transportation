package com.example.shipping.utils;

import com.example.shipping.entity.LoginUser;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.example.shipping.utils.JwtUtil;
import com.example.shipping.utils.RedisCache;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
/**
 * 该类用于从前端发送至后端的request中解析token获得当前用户信息
 */
public class GetLoginUser {
    @Autowired
    private static RedisCache redisCache;

    /**
     * 从前端发送至后端的request中解析token获得当前用户信息
     * @param request
     * @return LoginUser 封装了用户信息
     */
    public static LoginUser getLoginUser(HttpServletRequest request){
        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            return null;
        }
        // 解析token
        String userId;
        try {
            Claims claim = JwtUtil.parseJWT(token);
            userId = claim.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("非法token");
        }
        // 从redis中获取用户信息
        String redisKey = "Login:" + userId;
        LoginUser loginUser = JSON.parseObject(JSON.toJSONString(redisCache.getCacheObject(redisKey)), LoginUser.class);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }
        return loginUser;
    }
}
