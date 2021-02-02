package com.lhf.springsecuritydemo01.controller;

import com.lhf.springsecuritydemo01.bean.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: lhf
 * @Date: 2021/1/2 19:33
 */
@RestController

public class HelloSpringSecurityController {

    @GetMapping("/hello")
    public String  getHello(){
        return "Hello->SpringSecurity";
    }

    @GetMapping("/index")

    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public UserInfo getUserInfo(){
        return new UserInfo(0L, "嘟嘟", "xxxx", new Date());
    }
}
