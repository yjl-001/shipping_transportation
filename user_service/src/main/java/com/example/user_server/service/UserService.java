package com.example.user_server.service;

import com.example.user_server.dao.UserDao;
import com.example.user_server.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查找用户信息
     * @param username 用户名
     * @return UserDto 用户信息
     */
    public UserDao getUser(String username){
        return userMapper.getUser(username);
    }

    /**
     *
     * @return
     */
    public UserDao getUserByUserId(Integer userId){
        return userMapper.getUserByUserId(userId);
    }

    /**
     * 向数据库中添加一条用户信息
     * @param username 用户名
     * @param password 密码
     * @param email 邮件
     * @param sex 性别
     * @return
     */
    public void insertUser(String username, String password, String email, String sex,Integer role_id){
        UserDao user = new UserDao();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setSex(sex);
        user.setRole_id(role_id);
        userMapper.insertUser(user);
    }
}
