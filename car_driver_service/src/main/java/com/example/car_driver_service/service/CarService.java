package com.example.car_driver_service.service;

import java.util.List;

import com.example.car_driver_service.dao.CarDao;
import com.example.car_driver_service.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CarService {
   @Autowired
   private CarMapper carMapper;

    /**
     *
     * @return
     */
    public List<CarDao> getAllCarsByCompanyId(Integer userId){
        List<CarDao> cars = carMapper.getAllCarsByCompanyId(userId);
        for(CarDao car:cars){
            if(car.getDriver_id()==null)
                car.setStatue("空闲");
            else
                car.setStatue("工作");
        }
        return cars;
    }

    /**
     * 根据公司ID获得所有公司名下空闲的车辆
     * @return
     */
    public List<CarDao> getAllFreeCarsByCompayId(Integer userId){
        return carMapper.getAllFreeCarsByCompayId(userId);
    }

    /**
     *
     * @param carDao
     */
    public void insertCar(CarDao carDao,Integer userId){
        carDao.setCompany_id(userId);
        carMapper.insertCar(carDao);
    }

    /**
     *
     * @param carId
     */
    public void updataCarStatueFree(String carId){
        carMapper.updataCarStatueFree(carId);
    }

    /**
     *
     * @param carId
     * @param driverId
     */
    public void updataCarStatueUse(String carId, Integer driverId){
        carMapper.updataCarStatueUse(carId, driverId);
    }
}
