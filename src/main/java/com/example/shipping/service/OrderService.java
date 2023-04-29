package com.example.shipping.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shipping.entity.GoodsDao;
import com.example.shipping.entity.LoginUser;
import com.example.shipping.entity.OrderDao;
import com.example.shipping.mapper.CarMapper;
import com.example.shipping.mapper.DriverMapper;
import com.example.shipping.mapper.GoodsMapper;
import com.example.shipping.mapper.OrderMapper;
import com.example.shipping.utils.GetLoginUser;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class OrderService {
    @Autowired
    private GetLoginUser getLoginUser;
    @Autowired
    private HttpServletRequest request;
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
    public void createOrder(String goodsId,String driverId,String carId){
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        //创建order实例
        OrderDao orderDto = new OrderDao();
        GoodsDao goodsDto = goodsMapper.getGoodsById(Integer.valueOf(goodsId));
        orderDto.setGoodsId(Integer.valueOf(goodsId));
        orderDto.setCarId(carId);
        orderDto.setDriverId(Integer.valueOf(driverId));
        orderDto.setCompanyId(loginUser.getUser().getId());
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
    public List<OrderDao> getOrdersByUser(){
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        return orderMapper.getOrdersByUser(loginUser.getUser().getId());
    }

    /**
     * 
     * @return
     */
    public List<OrderDao> getOrdersByShop(){
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        return orderMapper.getOrdersByShop(loginUser.getUser().getId());
    }
}
