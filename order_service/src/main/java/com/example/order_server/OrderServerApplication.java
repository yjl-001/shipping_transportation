package com.example.order_server;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication(scanBasePackages = {"com.example.goods_server","com.example.car_driver_service"})
@EnableDiscoveryClient
@EnableKafka
@EnableKafkaStreams
public class OrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }

    // 定义两个topic，分别用于向car_driver_service发送消息
    @Bean
    public NewTopic goods_order() {
        return TopicBuilder.name("goods-order")
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic car_order() {
        return TopicBuilder.name("car-order")
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic driver_order(){
        return TopicBuilder.name("driver-order")
                .partitions(3)
                .compact()
                .build();
    }
}
