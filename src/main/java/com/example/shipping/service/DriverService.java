package com.example.shipping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shipping.entity.DriverDao;
import com.example.shipping.entity.LoginUser;
import com.example.shipping.mapper.DriverMapper;
import com.example.shipping.utils.GetLoginUser;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DriverService {
    @Autowired
    private GetLoginUser getLoginUser;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    DriverMapper driverMapper;

    /**
     * 
     * @return
     */
    public List<DriverDao> getAllFreeDriversByCompayId(){
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        return driverMapper.getAllFreeDriversByCompanyId(loginUser.getUser().getId());
    }

    /**
     * 
     * @return
     */
    public List<DriverDao> getAllDriversByCompayId() {
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        List<DriverDao> drivers = driverMapper.getAllDriversByCompanyId(loginUser.getUser().getId());
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
    public void insertDriver(DriverDao driverDao){
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        driverDao.setCompany_id(loginUser.getUser().getId());
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
