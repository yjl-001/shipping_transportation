package com.example.shipping.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.shipping.entity.LoginUser;
import com.example.shipping.entity.UserDao;
import com.example.shipping.utils.JwtUtil;
import com.example.shipping.utils.RedisCache;
import com.example.shipping.utils.ResponseResult;

@Service
public class LoginService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    
    /**
     * 验证登录用户的用户名和密码是否正确，如果认证通过，将信息保存至redis中
     * @param user
     * @return ResponseResult 包括状态码、验证信息以及认证通过后生成的jwt
     */
    public ResponseResult login(UserDao user){
        //利用AuthenticationManager认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //认证失败
        if(Objects.isNull(authentication)){
            throw new RuntimeException("登录失败");
        }
        //认证通过
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        Map<String, String>map = new HashMap<>();
        map.put("token", jwt);
        //保存信息至redis中
        redisCache.setCacheObject("Login:"+userid, loginUser);
        return new ResponseResult<Map<String,String>>(200, "登录成功",map);
    }
}
