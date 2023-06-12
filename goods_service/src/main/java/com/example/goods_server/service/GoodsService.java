package com.example.goods_server.service;

import java.util.List;

import com.example.goods_server.mapper.GoodsMapper;
import com.example.goods_server.dao.GoodsDao;
import com.example.utils_service.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.servlet.http.HttpServletRequest;

@Service
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private HttpServletRequest request;

    /**
     * 创建一个商品
     * @param goodsDto
     * @return ResponseResult, 包含状态码，描述信息和返回的数据
     */
    public ResponseResult createGoods(GoodsDao goodsDto,Integer userId){
        //添加goodsDto缺失的数据
        goodsDto.setConsigner_id(userId);
        goodsMapper.insetGoods(goodsDto);
        return new ResponseResult<>(200, "创建成功", null);
    }

    /**
     * 获得所有未在运输中的商品
     * @return
     */
    public List<GoodsDao> getAllUntranspotedGoods(){
        return goodsMapper.getAllUntranspotedGoods();
    }
}
