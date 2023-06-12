package com.example.user_server.service;

import com.example.user_server.utils.ResponseResult;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Component
@Service
//@FeignClient("CAR-DRIVER-SERVICE")
public interface CarDriverService {
    @RequestMapping(value = "/company/{id}/cars-drivers", method = RequestMethod.GET)
    ResponseResult getCarsDrivers(@PathParam("id")Integer userId);
}
