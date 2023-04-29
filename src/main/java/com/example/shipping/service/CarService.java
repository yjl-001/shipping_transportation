package com.example.shipping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shipping.entity.CarDao;
import com.example.shipping.entity.LoginUser;
import com.example.shipping.mapper.CarMapper;
import com.example.shipping.utils.GetLoginUser;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CarService {
    @Autowired
    private GetLoginUser getLoginUser;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private 
    CarMapper carMapper;

    /**
     * 
     * @return
     */
    public List<CarDao> getAllCarsByCompanyId(){
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        List<CarDao> cars = carMapper.getAllCarsByCompanyId(loginUser.getUser().getId());
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
    public List<CarDao> getAllFreeCarsByCompayId(){
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        return carMapper.getAllFreeCarsByCompayId(loginUser.getUser().getId());
    }

    /**
     * 
     * @param carDao
     */
    public void insertCar(CarDao carDao){
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        carDao.setCompany_id(loginUser.getUser().getId());
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
