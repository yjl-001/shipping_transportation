package com.example.shipping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.shipping.entity.GoodsDto;

import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface GoodsMapper {
    /*
     * 插入一个商品
     */
    @Insert("INSERT INTO goods(goods_description, begin_addr,dest_addr,price,create_time,dest_time,consigner_id) values(#{goodsDto.goods_description},#{goodsDto.begin_addr},#{goodsDto.dest_addr},#{goodsDto.price},#{goodsDto.create_time},#{goodsDto.dest_time},#{goodsDto.consigner_id})")
    void insetGoods(@Param(value = "goodsDto")GoodsDto goodsDto);

    /*
     * 列出所有已上传的商品
     */
    @Select("SELECT goods.goods_description, goods.begin_addr,goods.dest_addr,goods.price,goods.create_time,goods.dest_time FROM goods WHERE goods.consigner_id = #{userId}")
    List<GoodsDto> getAllGoods(@Param(value="userId")Integer userId);
}
