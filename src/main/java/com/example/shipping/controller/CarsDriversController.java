package com.example.shipping.controller;

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

@RestController
public class CarsDriversController {
    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;

    /**
     * Controller 获得公司名下所有的车辆和司机
     * @return RespnseResult
     */
    @RequestMapping(value = "/company/cars-drivers", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult getCarsDrivers(){
        Map<String, Object> map = new HashMap<>();
        List<CarDao> cars = carService.getAllFreeCarsByCompayId();
        List<DriverDao> drivers = driverService.getAllFreeDriversByCompayId();
        map.put("cars", cars);
        map.put("drivers",drivers);
        return new ResponseResult<Map<String,Object>>(200, "success", map);
    }

    /**
     * Controller 增加一个司机
     * @param driverDao 前端传给后端的数据
     * @return ResponseResult
     */
    @RequestMapping(value = "/company/driver", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult addDriver(DriverDao driverDao){
        driverService.insertDriver(driverDao);
        return new ResponseResult<>(200, "success", null);
    }

    /**
     * Controller 增加一辆货车
     * @param carDao 前端传给后端的数据
     * @return ResponseResult
     */
    @RequestMapping(value = "/company/car", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult addCar(CarDao carDao) {
        carService.insertCar(carDao);
        return new ResponseResult<>(200, "success", null);
    }
}
