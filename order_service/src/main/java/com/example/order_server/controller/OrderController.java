package com.example.order_server.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.order_server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.config_service.utils.ResponseResult;

import jakarta.websocket.server.PathParam;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * Controller 承运商接单，创建订单
     * @param order
     * @return
     */
    @RequestMapping(value = "/company/{id}/order", method = RequestMethod.POST)
    public ResponseResult createOrder(@RequestBody Map<String,String> order,@PathParam("id")Integer userId){
        String goodsId = order.get("goodsId");
        String driverId = order.get("driverId");
        String carId = order.get("carId");
        orderService.createOrder(goodsId, driverId, carId,userId);
        return new ResponseResult<>(200, "success", null);
    }

    /**
     * Controller 承运商更新订单状态
     * @param orderId
     * @param status
     * @param now_addr
     * @return
     */
    @RequestMapping(value = "/company/order/{orderId}/{status}/{now_addr}", method = RequestMethod.PUT)
    public ResponseResult updateOrder(@PathParam(value = "orderId") String orderId,@PathParam(value = "status") String status, @PathParam(value = "now_addr") String now_addr){
        orderService.updateOrder(orderId,status,now_addr);
        return new ResponseResult<>(200, "success", null);
    }

    /**
     * Controller 商家更改订单状态为已签收
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/company/order/{orderId}/statue/signed", method = RequestMethod.PUT)
    public ResponseResult updateOrderSign(@PathParam(value = "orderId")String orderId){
        orderService.updateOrderSign(orderId);
        return new ResponseResult<>(200, "success", null);
    }

    @RequestMapping(value = "/consigner/{id}/orders",method = RequestMethod.GET)
    public ResponseResult getOrders(@PathParam("id")Integer userId){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("orders", orderService.getOrdersByUser(userId));
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }

    @RequestMapping(value = "/company/{id}/orders", method = RequestMethod.GET)
    public ResponseResult getShopOrder(@PathParam("id")Integer userId) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("orders", orderService.getOrdersByShop(userId));
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }
}

