package com.example.user_server.controller;

import com.example.car_driver_service.service.CarService;
import com.example.car_driver_service.service.DriverService;
import com.example.user_server.dao.UserDao;
import com.example.user_server.service.UserService;
import com.example.config_service.utils.ResponseResult;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;

    @RequestMapping(value = "/consigner/{id}/info", method = RequestMethod.GET)
    public ResponseResult getConsignerInfo(@PathVariable("id")Integer userId) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userService.getUserByUserId(userId));
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public String hello(){
        System.out.println("hhhh");
        return "hello";
    }

    @RequestMapping(value = "/company/{id}/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getCompanyInfo(@PathVariable("id")Integer userId) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userService.getUserByUserId(userId));
        attributes.put("cars", carService.getAllCarsByCompanyId(userId));
        attributes.put("drivers", driverService.getAllDriversByCompayId(userId));
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }

    /**
     * Controller 用户注册
     * @param userDto
     * @return 注册成功后返回登录界面
     */
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ResponseResult register(@RequestBody UserDao userDto) {
        userService.insertUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(),userDto.getSex(),userDto.getRole_id());
        return new ResponseResult<UserDao>(200, "success", userDto);
    }
}
