package com.example.user_server.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String sex;
    private Integer role_id;
    private String role_key;
}
