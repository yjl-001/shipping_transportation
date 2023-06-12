package com.example.car_driver_service.service;

import java.util.List;

import com.example.car_driver_service.dao.DriverDao;
import com.example.car_driver_service.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.servlet.http.HttpServletRequest;

@Service
public class DriverService {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DriverMapper driverMapper;

    /**
     *
     * @return
     */
    public List<DriverDao> getAllFreeDriversByCompayId(Integer userId){
        return driverMapper.getAllFreeDriversByCompanyId(userId);
    }

    /**
     *
     * @return
     */
    public List<DriverDao> getAllDriversByCompayId(Integer userId) {
        List<DriverDao> drivers = driverMapper.getAllDriversByCompanyId(userId);
        for(DriverDao driver: drivers){
            if(driver.getStatue()==0)
                driver.setStatue_desc("空闲");
            else
                driver.setStatue_desc("工作");
        }
        return drivers;
    }

    /**
     *
     * @param driverDao
     */
    public void insertDriver(DriverDao driverDao,Integer userId){
        driverDao.setCompany_id(userId);
        driverMapper.insertDriver(driverDao);
    }

    /**
     *
     * @param driverId
     */
    public void updateDriverStatueBusyToFree(Integer driverId){
        driverMapper.updateDriverStatueBusyToFree(driverId);
    }

    /**
     *
     * @param driverId
     */
    public void updateDriverStatueFreeToBusy(Integer driverId){
        driverMapper.updateDriverStatueFreeToBusy(driverId);
    }
}

