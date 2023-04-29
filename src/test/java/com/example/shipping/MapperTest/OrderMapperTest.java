package com.example.shipping.MapperTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping.entity.OrderDao;
import com.example.shipping.mapper.OrderMapper;

@SpringBootTest
public class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;

    @Test
    public void getOrdersTest(){
        List<OrderDao> orders = orderMapper.getOrders();
        for(OrderDao order:orders){
            System.out.println(order.getDrivername());
        }
    }

    @Test
    public void getOrdersByUserTest(){
        List<OrderDao> orders = orderMapper.getOrdersByUser(1);
        for (OrderDao order : orders) {
            System.out.println(order.getDrivername());
        }
    }
}
