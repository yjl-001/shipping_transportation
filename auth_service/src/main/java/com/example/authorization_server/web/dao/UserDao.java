package com.example.authorization_server.web.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String sex;
    private Integer role_id;
    private String role_key;
}