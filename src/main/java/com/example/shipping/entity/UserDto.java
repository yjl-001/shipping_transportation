package com.example.shipping.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String sex;
}
