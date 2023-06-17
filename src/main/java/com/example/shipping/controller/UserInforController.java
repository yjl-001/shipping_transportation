package com.example.shipping.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shipping.service.CarService;
import com.example.shipping.service.DriverService;
import com.example.shipping.service.UserService;
import com.example.shipping.utils.ResponseResult;

@RestController
public class UserInforController {
    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;

    @RequestMapping(value = "/consigner/{id}/info", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('consigner')")
    public ResponseResult getConsignerInfo() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userService.getUserByUserId());
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }

    @RequestMapping(value = "/company/{id}/info", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult getCompanyInfo() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userService.getUserByUserId());
        attributes.put("cars", carService.getAllCarsByCompanyId());
        attributes.put("drivers", driverService.getAllDriversByCompayId());
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }
}
