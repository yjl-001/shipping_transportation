package com.example.shipping.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.example.shipping.entity.LoginUser;
import com.example.shipping.entity.UserDto;
import com.example.shipping.service.LoginService;
import com.example.shipping.service.UserService;
import com.example.shipping.utils.JwtUtil;
import com.example.shipping.utils.RedisCache;
import com.example.shipping.utils.ResponseResult;
import io.jsonwebtoken.Claims;


@RestController
public class LoginController {
    @Autowired
    private UserService service;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisCache redisCache;

    @RequestMapping(value="/user/login",method=RequestMethod.POST)
    public ModelAndView Login(UserDto user){
       ResponseResult responseResult = loginService.login(user);
       if(responseResult.getCode()==200){
         return loginSuccess(responseResult);
       }else{
        return new ModelAndView("login");
       }
    }

    @RequestMapping(value="/user/register", method=RequestMethod.POST)
    public ModelAndView register(UserDto userDto) {
        service.insertUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(),userDto.getSex());
        return new ModelAndView("login");
    }

    @RequestMapping(value="/register-view", method = RequestMethod.GET)
    public ModelAndView registerView() {
        return new ModelAndView("register");
    }

    public ModelAndView loginSuccess(ResponseResult responseResult) {
        String userId;
        try {
            Claims claim = JwtUtil.parseJWT((String) ((Map) responseResult.getData()).get("token"));
            userId = claim.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("非法token");
        }
        // 从redis中获取用户信息
        String redisKey = "Login:" + userId;
        LoginUser loginUser = JSON.parseObject(JSON.toJSONString(redisCache.getCacheObject(redisKey)), LoginUser.class);
        // 获取当前线程中用户的名称,将名称传递至页面
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("username", loginUser.getUsername());
        attributes.put("result", responseResult);
        return new ModelAndView("index", attributes);
    }

}
