package com.example.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shipping.entity.GoodsDto;
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

    /**
     * 创建一个商品
     * @param goodsDto
     * @return ResponseResult, 包含状态码，描述信息和返回的数据
     */
    public ResponseResult createGoods(GoodsDto goodsDto){
        //添加goodsDto缺失的数据
        LoginUser loginUser = GetLoginUser.getLoginUser(request);
        goodsDto.setConsigner_id(loginUser.getUser().getId());
        goodsMapper.insetGoods(goodsDto);
        return new ResponseResult<>(200, "创建成功", null);
    }
}
