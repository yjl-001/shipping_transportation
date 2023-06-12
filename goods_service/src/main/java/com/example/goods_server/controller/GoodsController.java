package com.example.goods_server.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.goods_server.dao.GoodsDao;
import com.example.goods_server.service.GoodsService;
import com.example.config_service.utils.ResponseResult;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * Controller 商家创建一个待运输的商品
     * @param goodsDao
     * @return ResponseResult
     */
    @RequestMapping(value = "/consigner/{id}/goods", method = RequestMethod.POST)
    public ResponseResult createGoods(@RequestBody GoodsDao goodsDao, @PathParam("id")Integer userId){
        return goodsService.createGoods(goodsDao,userId);
    }

    @RequestMapping(value = "/company/goods", method = RequestMethod.GET)
    public ResponseResult getShopGoods() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("goods", goodsService.getAllUntranspotedGoods());
        return new ResponseResult<Map<String,Object>>(200, "success", attributes);
    }
}
