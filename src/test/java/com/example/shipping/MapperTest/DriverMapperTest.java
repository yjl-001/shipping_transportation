package com.example.shipping.MapperTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping.entity.DriverDao;
import com.example.shipping.mapper.DriverMapper;

@SpringBootTest
public class DriverMapperTest {
    @Autowired
    private DriverMapper driverMapper;

    @Test
    public void getAllDriversByCompanyIdTest(){
        List<DriverDao> drivers = driverMapper.getAllDriversByCompanyId(2);
        System.out.println("-------test------------");
        for(DriverDao driver:drivers){
            System.out.println(driver.getDrivername());
        }
    }

    @Test
    public void getAllFreeDriversByCompanyIdTest(){
        List<DriverDao> drivers = driverMapper.getAllFreeDriversByCompanyId(2);
        System.out.println("-------test------------");
        for (DriverDao driver : drivers) {
            System.out.println(driver.getDrivername());
        }
    }

    @Test
    public void updateDriverStatueFreeToBusyTest(){
        driverMapper.updateDriverStatueFreeToBusy(1);
        driverMapper.updateDriverStatueFreeToBusy(2);
        getAllFreeDriversByCompanyIdTest();
    }

    @Test
    public void updateDriverStatueBusyToFreeTset(){
        driverMapper.updateDriverStatueBusyToFree(2);
        getAllFreeDriversByCompanyIdTest();
    }
}
