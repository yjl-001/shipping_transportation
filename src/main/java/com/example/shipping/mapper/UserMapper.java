package com.example.shipping.mapper;


import com.example.shipping.entity.UserDto;

import lombok.val;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    /*
     * 根据用户名查询用户
     */
    @Select("SELECT user.id,user.username,user.password,user.email,user.sex,role.role_key FROM user,role WHERE user.role_id = role.role_id and username = #{username}")
    UserDto getUser(@Param(value = "username") String username);

    /*
     * 添加一个用户
     */
    @Insert("INSERT INTO user(username, password, email, sex) VALUES (#{user.username}, #{user.password}, #{user.email}, #{user.sex})")
    void insertUser(@Param(value="user") UserDto user);

    /*
     * 更新用户名
     */
    @Update("UPDATE user set username=#{user.username} where id=#{user.id}")
    void updataUsername(@Param(value="user") UserDto user);

    /*
     * 更新用户的性别
     */
    @Update("UPDATE user set sex=#{user.sex} where id=#{user.id}")
    void updateUserSex(@Param(value="user") UserDto user);

    /*
     * 更新用户的邮件
     */
    @Update("UPDATE user set email=#{user.email} where id=#{user.id}")
    void updateUserEmail(@Param(value = "user") UserDto user);

    /*
     * 更新用户的用户名、性别、邮件
     */
    @Update("UPDATE user set username=#{user.username}, sex=#{user.sex},email=#{user.email} where id=#{user.id}")
    void updateUser(@Param(value = "user") UserDto user);
}

