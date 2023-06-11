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
import com.example.shipping.entity.UserDao;
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

    /**
     * Controller
     * 验证用户登录
     * @param user
     * @return 验证成功后返回进入主界面，失败后在原界面
     */
    @RequestMapping(value="/index",method=RequestMethod.POST)
    public ModelAndView Login(UserDao user){
       ResponseResult responseResult = loginService.login(user);
       if(responseResult.getCode()==200){
         return loginSuccess(responseResult);
       }else{
        return new ModelAndView("login");
       }
    }

    /**
     * Controller 用户注册
     * @param userDto
     * @return 注册成功后返回登录界面
     */
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public ModelAndView register(UserDao userDto) {
        service.insertUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(),userDto.getSex(),userDto.getRole_id());
        return new ModelAndView("login");
    }

    /**
     * 用户登录验证成功后，从JWT中获取用户ID,从redis中读取用户信息传递给界面，根据用户不同的身份信息返回不同的界面
     * @param responseResult
     * @return
     */
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
        if(loginUser.getUser().getRole_key().equals("company"))
            return new ModelAndView("viewOrderShop", attributes);
        else
            return new ModelAndView("viewOrderUser", attributes);
    }

}
