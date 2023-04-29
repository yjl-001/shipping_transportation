package com.example.shipping.MapperTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping.entity.GoodsDao;
import com.example.shipping.mapper.GoodsMapper;

@SpringBootTest
public class GoodsMapperTest {
    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void getAllUntranspotedGoodsTest(){
        List<GoodsDao> goods = goodsMapper.getAllUntranspotedGoods();
        for(GoodsDao gds : goods){
            System.out.println(gds.getGoods_description());
            System.out.println(gds.getUsername());
        }
    }
}
