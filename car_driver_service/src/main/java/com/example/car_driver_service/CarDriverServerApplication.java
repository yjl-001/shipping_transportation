package com.example.car_driver_service;

import com.example.car_driver_service.dao.CarDao;
import com.example.car_driver_service.dao.DriverDao;
import com.example.car_driver_service.service.CarService;
import com.example.car_driver_service.service.DriverService;
import com.example.config_service.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class CarDriverServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarDriverServerApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<Long, Message> template;
    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;

    // 监听topic cars
    @KafkaListener(id = "cars", topics = "cars", groupId = "car")
    public void onCarsEvent(Message u_message) {
        List<CarDao> cars = carService.getAllCarsByCompanyId((Integer) u_message.getData());
        Message<List<CarDao>> re_message = new Message<>(u_message.getKey(),cars);
        template.send("cars",u_message.getKey(),re_message);
    }

    // 监听topic drivers
    @KafkaListener(id = "drivers", topics = "drivers", groupId = "driver")
    public void onDriversEvent(Message u_message) {
        List<DriverDao> drivers = driverService.getAllDriversByCompayId((Integer) u_message.getData());
        Message<List<DriverDao>> re_message = new Message<>(u_message.getKey(),drivers);
        template.send("drivers",u_message.getKey(),re_message);
    }

}
