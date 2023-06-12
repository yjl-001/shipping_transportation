package com.example.order_server.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.car_driver_service.mapper.CarMapper;
import com.example.car_driver_service.mapper.DriverMapper;
import com.example.goods_server.mapper.GoodsMapper;
import com.example.order_server.dao.OrderDao;
import com.example.order_server.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     *
     * @param goodsId
     * @param driverId
     * @param carId
     */
    public void createOrder(String goodsId,String driverId,String carId,Integer userId){
        //创建order实例
        OrderDao orderDto = new OrderDao();
        GoodsDao goodsDto = goodsMapper.getGoodsById(Integer.valueOf(goodsId));
        orderDto.setGoodsId(Integer.valueOf(goodsId));
        orderDto.setCarId(carId);
        orderDto.setDriverId(Integer.valueOf(driverId));
        orderDto.setCompanyId(userId);
        orderDto.setBegin_addr(goodsDto.getBegin_addr());
        orderDto.setDest_addr(goodsDto.getDest_addr());
        orderDto.setDest_time(goodsDto.getDest_time());
        orderDto.setCreate_time(goodsDto.getCreate_time());
        orderDto.setPrice(goodsDto.getPrice());
        orderDto.setStatus("待发货");
        orderDto.setNow_addr(goodsDto.getBegin_addr());
        orderMapper.insertOrder(orderDto);
        //更新车辆状态
        carMapper.updataCarStatueUse(carId, Integer.valueOf(driverId));
        //更新司机状态
        driverMapper.updateDriverStatueFreeToBusy(Integer.valueOf(driverId));
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
            carMapper.updataCarStatueFree(order.getCarId());
            driverMapper.updateDriverStatueBusyToFree(order.getDriverId());
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
