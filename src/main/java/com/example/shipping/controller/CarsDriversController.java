package com.example.shipping.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.shipping.entity.CarDao;
import com.example.shipping.entity.DriverDao;
import com.example.shipping.service.CarService;
import com.example.shipping.service.DriverService;
import com.example.shipping.utils.ResponseResult;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@RestController
public class CarsDriversController {
    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;

    private final Bucket bucket;

    public CarsDriversController() {
        long num = 20;
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(num, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
            .addLimit(limit)
            .build();
    }

    /**
     * Controller 获得公司名下所有的车辆和司机
     * @return RespnseResult
     */
    @RequestMapping(value = "/company/{id}/cars-drivers", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult getCarsDrivers(){
        if(bucket.tryConsume(1)){
            Map<String, Object> map = new HashMap<>();
            List<CarDao> cars = carService.getAllFreeCarsByCompayId();
            List<DriverDao> drivers = driverService.getAllFreeDriversByCompayId();
            map.put("cars", cars);
            map.put("drivers",drivers);
            return new ResponseResult<Map<String,Object>>(200, "success", map);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }

    /**
     * Controller 增加一个司机
     * @param driverDao 前端传给后端的数据
     * @return ResponseResult
     */
    @RequestMapping(value = "/company/{id}/driver", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult addDriver(DriverDao driverDao){
        if(bucket.tryConsume(1)){
            driverService.insertDriver(driverDao);
            return new ResponseResult<>(200, "success", null);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }

    /**
     * Controller 增加一辆货车
     * @param carDao 前端传给后端的数据
     * @return ResponseResult
     */
    @RequestMapping(value = "/company/{id}/car", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult addCar(CarDao carDao) {
        if(bucket.tryConsume(1)){
            carService.insertCar(carDao);
            return new ResponseResult<>(200, "success", null);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }
}
