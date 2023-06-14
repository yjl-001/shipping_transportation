package com.example.user_server.controller;

import com.example.car_driver_service.service.CarService;
import com.example.car_driver_service.service.DriverService;
import com.example.config_service.utils.Message;
import com.example.user_server.dao.UserDao;
import com.example.user_server.service.UserService;
import com.example.config_service.utils.ResponseResult;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private KafkaTemplate<Long, Message> template;
    private AtomicLong id = new AtomicLong();

    @RequestMapping(value = "/consigner/{id}/info", method = RequestMethod.GET)
    public ResponseResult getConsignerInfo(@PathParam("id")Integer userId) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userService.getUserByUserId(userId));
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }

    @RequestMapping(value = "/company/{id}/info", method = RequestMethod.GET)
    public ResponseResult getCompanyInfo(@PathParam("id")Integer userId) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userService.getUserByUserId(userId));
        // 向topic cars and drivers 发送消息
        Message<Integer> car_message = new Message<>(id.incrementAndGet(),userId);
        CompletableFuture<SendResult<Long, Message>> car_future = template.send("cars",car_message.getKey(),car_message);
        try {
            attributes.put("cars",car_future.get().getProducerRecord().value().getData());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        Message<Integer> driver_message = new Message<>(id.incrementAndGet(),userId);
        CompletableFuture<SendResult<Long,Message>> driver_future = template.send("drivers",driver_message.getKey(),driver_message);
        //等待消费者返回消息
        try {
            attributes.put("drivers",driver_future.get().getProducerRecord().value().getData());
        }catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        //attributes.put("cars", carService.getAllCarsByCompanyId(userId));
        // 向topic drivers发送消息
        //attributes.put("drivers", driverService.getAllDriversByCompayId(userId));
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }

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
