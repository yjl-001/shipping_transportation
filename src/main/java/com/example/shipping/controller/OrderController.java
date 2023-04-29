package com.example.shipping.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shipping.service.OrderService;
import com.example.shipping.utils.ResponseResult;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    /**
     * Controller 承运商接单，创建订单
     * @param order
     * @return
     */
    @RequestMapping(value = "/user/createOrder", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult createOrder(@RequestBody Map<String,String> order){
        String goodsId = order.get("goodsId");
        String driverId = order.get("driverId");
        String carId = order.get("carId");
        orderService.createOrder(goodsId, driverId, carId);
        return new ResponseResult<>(200, "success", null);
    }

    /**
     * Controller 承运商更新订单状态
     * @param orderId
     * @param status
     * @param now_addr
     * @return
     */
    @RequestMapping(value = "/user/updateOrder", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult updateOrder(@RequestParam(name = "orderId") String orderId,@RequestParam(name = "status") String status, @RequestParam(name = "now_addr") String now_addr){
        orderService.updateOrder(orderId,status,now_addr);
        return new ResponseResult<>(200, "success", null);
    }

    /**
     * Controller 商家更改订单状态为已签收
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/user/updateOrderSign", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('consigner')")
    public ResponseResult updateOrderSign(@RequestParam(name = "orderId")String orderId){
        orderService.updateOrderSign(orderId);
        return new ResponseResult<>(200, "success", null);
    }
}
