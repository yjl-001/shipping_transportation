package com.example.shipping.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@RestController
public class GoodsController {
    
    @Autowired
    private GoodsService goodsService;

    // 添加Bucket4j限流
    private final Bucket bucket;

    public GoodsController() {
        long num = 20; // 最大限流数
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(num, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
            .addLimit(limit)
            .build();
    }

    /**
     * Controller 商家创建一个待运输的商品
     * @param goodsDto
     * @return ResponseResult
     */
    @RequestMapping(value = "/consigner/{id}/goods", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('consigner')")
    public ResponseResult createGoods(GoodsDao goodsDto){
        if(bucket.tryConsume(1)){
            return goodsService.createGoods(goodsDto);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }

    @RequestMapping(value = "/company/{id}/goods", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ResponseResult getShopGoods() {
        if(bucket.tryConsume(1)){
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("goods", goodsService.getAllUntranspotedGoods());
            return new ResponseResult<Map<String,Object>>(200, "success", attributes);
        }else{
            return new ResponseResult<>(505,"too many request",null);
        }
    }
}
