package com.example.car_driver_service;

import com.example.car_driver_service.dao.CarDao;
import com.example.car_driver_service.dao.DriverDao;
import com.example.car_driver_service.mapper.CarMapper;
import com.example.car_driver_service.mapper.DriverMapper;
import com.example.car_driver_service.service.CarService;
import com.example.car_driver_service.service.DriverService;
import com.example.config_service.utils.Message;
import com.example.order_server.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
@EnableKafkaStreams
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
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private DriverMapper driverMapper;

    // 监听topic cars
    @KafkaListener(id = "car", topics = "car", groupId = "car")
    public void onCarsEvent(Message u_message) {
        List<CarDao> cars = carService.getAllCarsByCompanyId((Integer) u_message.getData());
        Message<List<CarDao>> re_message = new Message<>(u_message.getKey(),cars);
        template.send("car",u_message.getKey(),re_message);
    }

    // 监听topic drivers
    @KafkaListener(id = "driver", topics = "driver", groupId = "driver")
    public void onDriversEvent(Message u_message) {
        List<DriverDao> drivers = driverService.getAllDriversByCompayId((Integer) u_message.getData());
        Message<List<DriverDao>> re_message = new Message<>(u_message.getKey(),drivers);
        template.send("driver",u_message.getKey(),re_message);
    }

    @KafkaListener(id = "car-order",topics = "car-order",groupId = "car-order")
    public void onCarOrder(Message u_message){
        String carId = ((OrderDao)u_message.getData()).getCarId();
        carMapper.updataCarStatueFree(carId);
    }

    @KafkaListener(id = "driver-order",topics = "driver-order",groupId = "driver-order")
    public void onDriver(Message u_message){
        Integer driverId = ((OrderDao)u_message.getData()).getDriverId();
        driverMapper.updateDriverStatueFreeToBusy(driverId);
    }

}
