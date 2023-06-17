package com.example.shipping.controller;

import java.time.Duration;
import java.util.HashMap;
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

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.websocket.server.PathParam;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    // 添加Bucket4j限流
    private final Bucket bucket;

    public OrderController() {
        long num = 20; // 最大限流数
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(num, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
            .addLimit(limit)
            .build();
    }
    
    /**
     * Controller 承运商接单，创建订单
     * @param order
     * @return
     */
    @RequestMapping(value = "/company/{id}/order", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult createOrder(@RequestBody Map<String,String> order){
        if(bucket.tryConsume(1)){
            String goodsId = order.get("goodsId");
            String driverId = order.get("driverId");
            String carId = order.get("carId");
            orderService.createOrder(goodsId, driverId, carId);
            return new ResponseResult<>(200, "success", null);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }

    /**
     * Controller 承运商更新订单状态
     * @param orderId
     * @param status
     * @param now_addr
     * @return
     */
    @RequestMapping(value = "/company/{id}/order/{orderId}/{status}/{now_addr}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult updateOrder(@PathParam(value = "orderId") String orderId,@PathParam(value = "status") String status, @PathParam(value = "now_addr") String now_addr){
        if(bucket.tryConsume(1)){
            orderService.updateOrder(orderId,status,now_addr);
            return new ResponseResult<>(200, "success", null);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }

    /**
     * Controller 商家更改订单状态为已签收
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/company/{id}/order/{orderId}/statue/signed", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('consigner')")
    public ResponseResult updateOrderSign(@PathParam(value = "orderId")String orderId){
        if(bucket.tryConsume(1)){
            orderService.updateOrderSign(orderId);
            return new ResponseResult<>(200, "success", null);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }

    @RequestMapping(value = "/consigner/{id}/orders",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('consigner')")
    public ResponseResult getOrders(){
        if(bucket.tryConsume(1)){
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("orders", orderService.getOrdersByUser());
            return new ResponseResult<Map<String,Object>>(200, "success", attributes);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }

    @RequestMapping(value = "/company/{id}/orders", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult getShopOrder() {
        if(bucket.tryConsume(1)){
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("orders", orderService.getOrdersByShop());
            return new ResponseResult<Map<String,Object>>(200, "success", attributes);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }
}
