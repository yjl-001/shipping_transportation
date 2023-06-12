package com.example.car_driver_service.mapper;

import java.util.List;

import com.example.car_driver_service.dao.DriverDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface DriverMapper {

    /**
     * 选择所有公司名下的司机
     * @param company_id，公司ID
     * @return
     */
    @Select("SELECT * from driver WHERE company_id = #{companyId}")
    public List<DriverDao> getAllDriversByCompanyId(@Param(value = "companyId")Integer company_id);

    /**
     * 选择所有公司名下状态空闲的司机
     * @param company_id
     * @return
     */
    @Select("SELECT * from driver WHERE company_id = #{companyId} AND statue=0")
    public List<DriverDao> getAllFreeDriversByCompanyId(@Param(value = "companyId") Integer company_id);

    /**
     * 更新司机的状态，由空闲至忙碌
     * @param id，司机的标识号
     */
    @Update("UPDATE driver SET statue=1 WHERE id=#{id}")
    public void updateDriverStatueFreeToBusy(@Param(value = "id") Integer id);

    /**
     * 更新司机的状态，由忙碌至空闲
     * @param id，司机的标识号
     */
    @Update("UPDATE driver SET statue=0 WHERE id=#{id}")
    public void updateDriverStatueBusyToFree(@Param(value = "id") Integer id);

    /**
     * 新增一位司机
     * @param driverDao
     */
    @Insert("INSERT INTO driver(drivername,phone,company_id,age,driving_age) VALUES(#{drivername},#{phone},#{company_id},#{age},#{driving_age})")
    public void insertDriver(@Param(value = "driverDao")DriverDao driverDao);
}
