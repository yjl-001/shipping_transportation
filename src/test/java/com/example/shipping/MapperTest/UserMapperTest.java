package com.example.shipping.MapperTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping.entity.UserDao;
import com.example.shipping.mapper.UserMapper;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserSelect(){
        UserDao user = userMapper.getUser("yjl");
        System.out.println(user.getRole_key());
    }

    @Test
    public void testUserInsert(){
        UserDao user = new UserDao();
        user.setUsername("whd");
        user.setPassword("123456");
        user.setEmail("20301083@bjtu.edu.cn");
        user.setSex("女");
        userMapper.insertUser(user);
        UserDao whd = userMapper.getUser("whd");
        System.out.println(whd);
    }
}
