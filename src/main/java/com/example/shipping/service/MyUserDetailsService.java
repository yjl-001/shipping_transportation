package com.example.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.example.shipping.mapper.UserMapper;
import com.example.shipping.entity.LoginUser;
import com.example.shipping.entity.UserDto;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //将来连接数据库根据账号查询用户信息
        UserDto userDto = userMapper.getUser(username);
        //当查询此用户不存在时，将抛出用户名未找到异常
        if (userDto == null) {
            throw new UsernameNotFoundException("No such user found, the user name is: "+username);
        }
        return new LoginUser(userDto);
    }
    
}
