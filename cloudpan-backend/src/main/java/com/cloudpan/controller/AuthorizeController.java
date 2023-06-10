package com.cloudpan.controller;

import com.cloudpan.entity.Result;
import com.cloudpan.service.AuthorizeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    @Resource
    AuthorizeService authorizeService;
    @PostMapping("/validateEmail")
    public Result validateEmail(@RequestParam("email")String email){
        String key=authorizeService.sendValidateEmail(email);
        if(key.equals("验证码发送成功！")){
            return Result.success(key);
        } else {
            return  Result.error(key);
        }
    }
    @PostMapping("/register")
    public Result register(String username,String password,String email, String code){
        String key=authorizeService.register(username,password,email,code);
        if(key.equals("注册成功")){
            return Result.success(key);
        } else {
            return  Result.error(key);
        }
    }
}
