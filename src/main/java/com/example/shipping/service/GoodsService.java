package com.example.shipping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shipping.entity.GoodsDao;
import com.example.shipping.entity.LoginUser;
import com.example.shipping.mapper.GoodsMapper;
import com.example.shipping.utils.GetLoginUser;
import com.example.shipping.utils.ResponseResult;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private GetLoginUser getLoginUser;

    /**
     * 创建一个商品
     * @param goodsDto
     * @return ResponseResult, 包含状态码，描述信息和返回的数据
     */
    public ResponseResult createGoods(GoodsDao goodsDto){
        //添加goodsDto缺失的数据
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        goodsDto.setConsigner_id(loginUser.getUser().getId());
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
