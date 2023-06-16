package com.example.order_server.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import com.example.car_driver_service.mapper.CarMapper;
import com.example.car_driver_service.mapper.DriverMapper;
import com.example.config_service.utils.Message;
import com.example.goods_server.mapper.GoodsMapper;
import com.example.order_server.dao.OrderDao;
import com.example.order_server.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import com.example.goods_server.dao.GoodsDao;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private DriverMapper driverMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private KafkaTemplate<Long, Message> template;
    private AtomicLong id = new AtomicLong();

    /**
     *
     * @param goodsId
     * @param driverId
     * @param carId
     */
    public void createOrder(String goodsId,String driverId,String carId,Integer userId){
        //创建order实例
        OrderDao orderDto = new OrderDao();
        orderDto.setGoodsId(Integer.valueOf(goodsId));
        orderDto.setDriverId(Integer.valueOf(driverId));
        orderDto.setCarId(carId);
        orderDto.setCompanyId(userId);
        Message<OrderDao> order_message = new Message<>(id.incrementAndGet(),orderDto);
        CompletableFuture<SendResult<Long, Message>> order_future = template.send("goods-order",order_message.getKey(),order_message);
        try {
            orderDto = (OrderDao) order_future.get().getProducerRecord().value().getData();
            orderMapper.insertOrder(orderDto);
            //更新车辆状态
            carMapper.updataCarStatueUse(carId, Integer.valueOf(driverId));
            //更新司机状态
            driverMapper.updateDriverStatueFreeToBusy(Integer.valueOf(driverId));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param orderId
     * @param status
     * @param now_addr
     */
    public void updateOrder(String orderId, String status, String now_addr){
        orderMapper.updateOrder(Integer.valueOf(orderId), status, now_addr);
        if(status.equals("已送达")){
            OrderDao order = orderMapper.getOrderById(Integer.valueOf(orderId));
            Message<OrderDao> order_message = new Message<>(id.incrementAndGet(),order);
            // 向car_order 和driver_order两个topic发送事件
            template.send("car-order",order_message.getKey(),order_message);
            template.send("driver-order",order_message.getKey(),order_message);
            //carMapper.updataCarStatueFree(order.getCarId());
            //driverMapper.updateDriverStatueBusyToFree(order.getDriverId());
        }
    }

    /**
     *
     * @param orderId
     */
    public void updateOrderSign(String orderId){
        orderMapper.updateOrderSign(Integer.valueOf(orderId), "已签收");
    }

    /**
     *
     * @return
     */
    public List<OrderDao> getOrdersByUser(Integer userId){
        return orderMapper.getOrdersByUser(userId);
    }

    /**
     *
     * @return
     */
    public List<OrderDao> getOrdersByShop(Integer userId){
        return orderMapper.getOrdersByShop(userId);
    }
}
