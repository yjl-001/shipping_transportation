package com.example.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public UserDto getUser(String username){
        return userMapper.getUser(username);
    }

    public String insert(String username, String password, String email, String sex){
        UserDto user = new UserDto();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setSex(sex);
        userMapper.insert(user);
        return "Insert ( \"" + username + "\") OK!";
    }
}
