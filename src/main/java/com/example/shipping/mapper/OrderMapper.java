package com.example.shipping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.shipping.entity.OrderDao;

@Mapper
public interface OrderMapper {
    
    /**
     * 根据订单ID查询订单
     * @param id
     * @return OrderDao
     */
    @Select("SELECT * FROM `order` WHERE id = ${id}")
    OrderDao getOrderById(@Param(value = "id")Integer id);

    /**
     * 查询所有订单
     * @return List<OrderDao>
     */
    @Select("SELECT `order`.`id`,goods.goods_description,`order`.begin_addr,`order`.dest_addr,`order`.price, `user`.username as company_name,`order`.dest_time,driver.drivername,cars.id as carId,`order`.now_addr,`order`.`status` FROM `order`,`cars`,`driver`,`user`,`goods` WHERE `order`.`car_id`=`cars`.`id` AND `order`.`driverId`=`driver`.`id` AND `order`.`companyId`=`user`.`id` AND `order`.`goodsId`=`goods`.`id`")
    List<OrderDao> getOrders();

    /**
     * 根据商家ID查询商家的所有订单
     * @param consigner_id 商家ID
     * @return List<OrderDao>
     */
    @Select("SELECT `order`.`id`,goods.goods_description,`order`.begin_addr,`order`.dest_addr,`order`.price, `user`.username as company_name,`order`.dest_time,driver.drivername,cars.id as carId,`order`.now_addr,`order`.`status` FROM `order`,`cars`,`driver`,`user`,`goods` WHERE `order`.`car_id`=`cars`.`id` AND `order`.`driverId`=`driver`.`id` AND `order`.`companyId`=`user`.`id` AND `order`.`goodsId`=`goods`.`id` AND goods.consigner_id=#{consigner_id}")
    List<OrderDao> getOrdersByUser(@Param(value = "consigner_id") Integer consigner_id);

    /**
     * 根据承运商ID查询承运商运输的所有订单
     * @param companyId 承运商ID
     * @return List<OrderDao>
     */
    @Select("SELECT `order`.`id`,goods.goods_description,`order`.begin_addr,`order`.dest_addr, `order`.price, `user`.username as consigner_name,`order`.dest_time,driver.drivername,cars.id as carId,`order`.now_addr,`order`.`status`FROM`order`,`cars`,`driver`,`user`,`goods`WHERE`order`.`car_id`=`cars`.`id`AND`order`.`driverId`=`driver`.`id`AND`order`.`goodsId`=`goods`.`id`AND`goods`.`consigner_id`=`user`.`id`AND`order`.`companyId`=#{companyId}")
    List<OrderDao> getOrdersByShop(@Param(value="companyId")Integer companyId);

    /**
     * 插入一个订单
     * @param orderDto
     */
    @Insert("INSERT INTO `order`(create_time,price,status,begin_addr,dest_addr,now_addr,goodsId,companyId,driverId,dest_time,car_id) VALUES (#{orderDto.create_time},#{orderDto.price},#{orderDto.status},#{orderDto.begin_addr},#{orderDto.dest_addr},#{orderDto.now_addr},#{orderDto.goodsId},#{orderDto.companyId},#{orderDto.driverId},#{orderDto.dest_time},#{orderDto.carId})")
    void insertOrder(@Param(value="orderDto")OrderDao orderDto);

    /**
     * 更新订单的状态和地点
     * @param orderId 订单ID
     * @param status 欲更新的状态
     * @param now_addr 欲更新的地址
     */
    @Update("Update `order` SET status=#{status},now_addr=#{now_addr} WHERE id=#{orderId}")
    void updateOrder(@Param(value = "orderId")Integer orderId,@Param(value = "status")String status,@Param(value = "now_addr")String now_addr);

    /**
     * 更新订单的状态
     * @param orderId 订单ID
     * @param status 欲更新的状态
     */
    @Update("Update `order` SET status=#{status} WHERE id=#{orderId}")
    void updateOrderSign(@Param(value = "orderId") Integer orderId, @Param(value = "status") String status);
}
