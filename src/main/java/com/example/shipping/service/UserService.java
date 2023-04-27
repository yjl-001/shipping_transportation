package com.example.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.shipping.entity.UserDto;
import com.example.shipping.mapper.UserMapper;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查找用户信息
     * @param username 用户名
     * @return UserDto 用户信息
     */
    public UserDto getUser(String username){
        return userMapper.getUser(username);
    }

    /**
     * 向数据库中添加一条用户信息
     * @param username 用户名
     * @param password 密码
     * @param email 邮件
     * @param sex 性别
     * @return
     */
    public void insertUser(String username, String password, String email, String sex){
        UserDto user = new UserDto();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setSex(sex);
        userMapper.insertUser(user);
    }
}
