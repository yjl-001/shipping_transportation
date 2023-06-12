package com.example.goods_server.mapper;

import java.util.List;
import com.example.goods_server.dao.GoodsDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import lombok.val;

@Mapper
public interface GoodsMapper {
    /**
     * 插入一个商品
     * @param goods 商品
     */
    @Insert("INSERT INTO goods(goods_description, begin_addr,dest_addr,price,create_time,dest_time,consigner_id,demands) VALUES (#{goods_description},#{begin_addr},#{dest_addr},#{price},#{create_time},#{dest_time},#{consigner_id},#{demands})")
    void insetGoods(@Param(value = "goods")GoodsDao goods);

    /**
     * 列出用户所有已上传的商品
     * @param userId 商家ID
     * @return List<GoodsDao>
     */
    @Select("SELECT goods.goods_description, goods.begin_addr,goods.dest_addr,goods.price,goods.create_time,goods.dest_time FROM goods WHERE goods.consigner_id = #{userId}")
    List<GoodsDao> getAllGoodsByUser(@Param(value="userId")Integer userId);

    /**
     *列出所有未被运输的商品
     *@return List<GoodsDao>
     */
    @Select("SELECT goods.id,goods.goods_description,goods.begin_addr,goods.dest_addr,goods.price,goods.create_time,goods.dest_time,goods.demands,user.username FROM `goods`,`user` WHERE goods.consigner_id=user.id AND goods.id NOT IN (SELECT  DISTINCT goodsId FROM `order`)")
    List<GoodsDao> getAllUntranspotedGoods();

    /**
     * 根据商品查询商品
     * @param id 商品ID
     * @return GoodsDao
     */
    @Select("SELECT * FROM goods WHERE id = #{id}")
    GoodsDao getGoodsById(@Param(value="id")Integer id);
}
