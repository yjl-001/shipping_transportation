package com.example.shipping.MapperTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping.entity.CarDao;
import com.example.shipping.mapper.CarMapper;

@SpringBootTest
public class CarMapperTest {
    @Autowired
    private CarMapper carMapper;

    @Test
    public void getAllFreeCarsByCompayIdTest(){
        List<CarDao> cars = carMapper.getAllFreeCarsByCompayId(2);
        for(CarDao car: cars){
            System.out.println(car.getId());
        }
    }

    @Test
    public void updataCarStatueUseTest(){
        carMapper.updataCarStatueUse("京A666", 1);
        getAllFreeCarsByCompayIdTest();
    }

    @Test
    public void updataCarStatueFreeTest(){
        carMapper.updataCarStatueFree("京A666");
        getAllFreeCarsByCompayIdTest();
    }
}
