package com.example.shipping.MapperTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping.entity.UserDto;
import com.example.shipping.mapper.UserMapper;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserSelect(){
        UserDto user = userMapper.getUser("yjl");
        System.out.println(user.getRole_key());
    }

    @Test
    public void testUserInsert(){
        UserDto user = new UserDto();
        user.setUsername("whd");
        user.setPassword("123456");
        user.setEmail("20301083@bjtu.edu.cn");
        user.setSex("女");
        userMapper.insertUser(user);
        UserDto whd = userMapper.getUser("whd");
        System.out.println(whd);
    }
}
