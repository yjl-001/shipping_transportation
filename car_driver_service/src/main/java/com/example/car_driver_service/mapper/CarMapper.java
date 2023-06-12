package com.example.car_driver_service.mapper;


import com.example.car_driver_service.dao.CarDao;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CarMapper {
    /**
     * 根据公司ID获取公司名下所有车辆
     * @param companyId，所属公司ID
     * @return
     */
    @Select("SELECT * FROM cars WHERE company_id = #{companyId}")
    List<CarDao> getAllCarsByCompanyId(@Param(value = "companyId") Integer companyId);

    /**
     * 根据公司ID获取公司名下的空闲的车辆
     * @param companyId，所属公司ID
     */
    @Select("SELECT * FROM cars WHERE company_id = #{companyId} AND driver_id IS NULL")
    List<CarDao> getAllFreeCarsByCompayId(@Param(value = "companyId") Integer companyId);

    /**
     * 更新车辆在使用
     * @param carId，车牌号
     * @param driverId，正在使用司机的ID
     */
    @Update("UPDATE cars SET driver_id=#{driverId} WHERE id=#{carId}")
    void updataCarStatueUse(@Param(value="carId")String carId,  @Param(value = "driverId") Integer driverId);

    /**
     * 更新车辆为空闲状态
     * @param carId，车牌号
     */
    @Update("UPDATE cars SET driver_id=NULL WHERE id=#{carId}")
    void updataCarStatueFree(@Param(value = "carId") String carId);

    /**
     * 新增一辆货车
     */
    @Insert("INSERT INTO cars(id,company_id,car_age,limit_weight) VALUES (#{id},#{company_id},#{car_age},#{limit_weight})")
    void insertCar(@Param(value = "carDao") CarDao carDao);
}
