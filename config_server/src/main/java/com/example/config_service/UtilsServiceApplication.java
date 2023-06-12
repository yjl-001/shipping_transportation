package com.example.config_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class UtilsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilsServiceApplication.class, args);
    }

}
