package com.example.shipping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.shipping.entity.GoodsDao;
import com.example.shipping.service.GoodsService;
import com.example.shipping.utils.ResponseResult;

@RestController
public class GoodsController {
    
    @Autowired
    private GoodsService goodsService;

    /**
     * Controller 商家创建一个待运输的商品
     * @param goodsDto
     * @return ResponseResult
     */
    @RequestMapping(value = "/user/addGoods", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('consigner')")
    public ResponseResult createGoods(GoodsDao goodsDto){
        return goodsService.createGoods(goodsDto);
    }
}
