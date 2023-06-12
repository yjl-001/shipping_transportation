package com.example.authorization_server.web.mapper;

import com.example.authorization_server.web.dao.UserDao;
import lombok.val;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return UserDao
     */
    @Select("SELECT user.id,user.username,user.password,user.email,user.sex,role.role_key FROM user,role WHERE user.role_id = role.role_id and username = #{username}")
    UserDao getUser(@Param(value = "username") String username);

    /**
     * 根据用户Id查询用户
     * @param id 用户ID
     * @return UserDao
     */
    @Select("SELECT user.id,user.username,user.password,user.email,user.sex,role.role_key FROM user,role WHERE user.role_id = role.role_id and user.id = #{userId}")
    UserDao getUserByUserId(@Param(value="userId")Integer id);

    /**
     * 添加一个用户
     * @param user 用户数据
     */
    @Insert("INSERT INTO user(username, password, email, sex,role_id) VALUES (#{user.username}, #{user.password}, #{user.email}, #{user.sex},#{user.role_id})")
    void insertUser(@Param(value="user") UserDao user);

    /**
     * 更新用户名
     * @param user 新用户数据
     */
    @Update("UPDATE user set username=#{user.username} where id=#{user.id}")
    void updataUsername(@Param(value="user") UserDao user);

    /**
     * 更新用户的性别
     * @param user 新用户数据
     */
    @Update("UPDATE user set sex=#{user.sex} where id=#{user.id}")
    void updateUserSex(@Param(value="user") UserDao user);

    /**
     * 更新用户的邮件
     * @param user 新用户数据
     */
    @Update("UPDATE user set email=#{user.email} where id=#{user.id}")
    void updateUserEmail(@Param(value = "user") UserDao user);

    /**
     * 更新用户的用户名、性别、邮件
     * @param user 新用户数据
     */
    @Update("UPDATE user set username=#{user.username}, sex=#{user.sex},email=#{user.email} where id=#{user.id}")
    void updateUser(@Param(value = "user") UserDao user);
}

