package com.example.websocket.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Controller
public class LoginController {


    @GetMapping("/loginpage")
    public String getLoginPage(){
        log.info("请求登录页面");
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam Map<String, Object> map){

        log.info("登录用户: [{}]，登录密码: [{}]",map.get("username").toString(),map.get("password").toString());
        return "index";
    }

}
