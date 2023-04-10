package com.example.shipping.mapper;


import com.example.shipping.entity.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM user WHERE username = #{username}")
    UserDto getUser(@Param(value = "username") String username);

    @Insert("INSERT INTO user(username, password, email, sex) VALUES (#{user.username}, #{user.password}, #{user.email}, #{user.sex})")
    void insert(@Param(value="user") UserDto user);
}

