package com.example.shipping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/dlogin")
    public String login(){
        return "dlogin";
    }
}
