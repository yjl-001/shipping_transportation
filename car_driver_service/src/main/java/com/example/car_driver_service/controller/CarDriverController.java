package com.example.car_driver_service.controller;

import com.example.car_driver_service.dao.CarDao;
import com.example.car_driver_service.dao.DriverDao;
import com.example.car_driver_service.service.CarService;
import com.example.car_driver_service.service.DriverService;
import com.example.utils_service.utils.ResponseResult;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CarDriverController {
    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;

    /**
     * Controller 获得公司名下所有的车辆和司机
     * @return RespnseResult
     */
    @RequestMapping(value = "/company/{id}/cars-drivers", method = RequestMethod.GET)
    public ResponseResult getCarsDrivers(@PathParam("id")Integer userId){
        Map<String, Object> map = new HashMap<>();
        List<CarDao> cars = carService.getAllFreeCarsByCompayId(userId);
        List<DriverDao> drivers = driverService.getAllFreeDriversByCompayId(userId);
        map.put("cars", cars);
        map.put("drivers",drivers);
        return new ResponseResult<Map<String,Object>>(200, "success", map);
    }




    /**
     * Controller 增加一个司机
     * @param driverDao 前端传给后端的数据
     * @return ResponseResult
     */
    @RequestMapping(value = "/company/{id}/driver", method = RequestMethod.POST)
    public ResponseResult addDriver(@RequestBody DriverDao driverDao,@PathParam("id")Integer userId){
        driverService.insertDriver(driverDao,userId);
        return new ResponseResult<>(200, "success", null);
    }

    /**
     * Controller 增加一辆货车
     * @param carDao 前端传给后端的数据
     * @return ResponseResult
     */
    @RequestMapping(value = "/company/car", method = RequestMethod.POST)
    public ResponseResult addCar(@RequestBody CarDao carDao,@PathParam("id")Integer userId) {
        carService.insertCar(carDao,userId);
        return new ResponseResult<>(200, "success", null);
    }
}
