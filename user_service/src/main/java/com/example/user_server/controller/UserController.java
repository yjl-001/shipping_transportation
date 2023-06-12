package com.example.user_server.controller;

import com.example.user_server.dao.UserDao;
import com.example.user_server.service.CarDriverService;
import com.example.user_server.service.UserService;
import com.example.user_server.utils.ResponseResult;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
//    @Autowired
//    private CarDriverService carDriverService;

    @RequestMapping(value = "/consigner/{id}/info", method = RequestMethod.GET)
    public ResponseResult getConsignerInfo(@PathParam("id")Integer userId) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userService.getUserByUserId(userId));
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }

//    @RequestMapping(value = "/company/{id}/info", method = RequestMethod.GET)
//    public ResponseResult getCompanyInfo(@PathParam("id")Integer userId) {
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("user", userService.getUserByUserId(userId));
//        attributes.put("cars", ((Map<String,Object>)(carDriverService.getCarsDrivers(userId).getData())).get("cars"));
//        attributes.put("drivers", ((Map<String,Object>)(carDriverService.getCarsDrivers(userId).getData())).get("drivers"));
//        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
//    }

    /**
     * Controller 用户注册
     * @param userDto
     * @return 注册成功后返回登录界面
     */
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public ResponseResult register(UserDao userDto) {
        userService.insertUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(),userDto.getSex(),userDto.getRole_id());
        return new ResponseResult<UserDao>(200, "success", userDto);
    }
}
