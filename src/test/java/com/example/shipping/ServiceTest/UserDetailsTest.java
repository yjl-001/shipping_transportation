package com.example.shipping.ServiceTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shipping.service.MyUserDetailsService;

@SpringBootTest
public class UserDetailsTest {
    @Autowired
    MyUserDetailsService userDetails;

    @Test
    public void loadUserByUsernameTest(){
        System.out.println(userDetails.loadUserByUsername("yjl"));
    }
}
