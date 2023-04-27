package com.example.shipping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.shipping.entity.GoodsDto;
import com.example.shipping.service.GoodsService;
import com.example.shipping.utils.ResponseResult;

@RestController
public class GoodsController {
    
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/consigner/create_goods", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('consigner')")
    public ResponseResult createGoods(GoodsDto goodsDto){
        return goodsService.createGoods(goodsDto);
    }
}
