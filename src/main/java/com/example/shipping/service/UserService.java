package com.example.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.shipping.entity.LoginUser;
import com.example.shipping.entity.UserDao;
import com.example.shipping.mapper.UserMapper;
import com.example.shipping.utils.GetLoginUser;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    private GetLoginUser getLoginUser;
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
    public UserDao getUserByUserId(){
        LoginUser loginUser = getLoginUser.getLoginUser(request);
        return userMapper.getUserByUserId(loginUser.getUser().getId());
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
