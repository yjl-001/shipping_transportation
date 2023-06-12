package com.example.car_driver_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CarDriverServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarDriverServerApplication.class, args);
    }

}
