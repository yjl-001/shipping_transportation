package com.example.user_server;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = {"com.example.car_driver_service"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableKafkaStreams
@EnableAsync
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 定义两个topic，分别用于向car_driver_service发送消息
    @Bean
    public NewTopic cars() {
        return TopicBuilder.name("car")
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic drivers() {
        return TopicBuilder.name("driver")
                .partitions(3)
                .compact()
                .build();
    }
}
