package com.example.goods_server;

import com.example.config_service.utils.Message;
import com.example.goods_server.dao.GoodsDao;
import com.example.goods_server.mapper.GoodsMapper;
import com.example.order_server.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication(scanBasePackages = "com.example.order_server")
@EnableDiscoveryClient
@EnableKafka
@EnableKafkaStreams
public class GoodsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsServerApplication.class, args);
    }
    @Autowired
    private KafkaTemplate<Long, Message> template;
    @Autowired
    private GoodsMapper goodsMapper;
    @KafkaListener(id = "goods-order",topics = "goods-order",groupId = "goods-order")
    public void onGoodsOrder(Message u_message){
        OrderDao order = (OrderDao) u_message.getData();
        GoodsDao goodsDto = goodsMapper.getGoodsById(order.getGoodsId());
        order.setGoodsId(order.getGoodsId());
        order.setCarId(String.valueOf(order.getCarId()));
        order.setDriverId(order.getDriverId());
        order.setCompanyId(order.getCompanyId());
        order.setBegin_addr(goodsDto.getBegin_addr());
        order.setDest_addr(goodsDto.getDest_addr());
        order.setDest_time(goodsDto.getDest_time());
        order.setCreate_time(goodsDto.getCreate_time());
        order.setPrice(goodsDto.getPrice());
        order.setNow_addr(goodsDto.getBegin_addr());
        order.setStatus("待发货");
        Message<OrderDao> re_message = new Message<>(u_message.getKey(),order);
        template.send("goods-order",u_message.getKey(),re_message);
    }
}
