package com.cloudpan.controller;

import com.cloudpan.entity.Account;
import com.cloudpan.entity.Result;
import com.cloudpan.service.AuthorizeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    @Resource
    AuthorizeService authorizeService;
    @GetMapping("/getData")
    public Result getData(){
        return Result.success(new Account(1,"163.com","lz805","123456"));
    }
    @PostMapping("/validateEmail")
    public Result validateEmail(@RequestParam("email")String email){
        String key=authorizeService.sendValidateEmail(email);
        if(key.equals("验证码发送成功！")){
            return Result.success(key);
        } else {
            return  Result.error(key);
        }
    }
    @PostMapping("/validateEmail2")
    public Result validateEmail2(@RequestParam("email")String email){
        String key=authorizeService.sendValidateEmail2(email);
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
    @PostMapping("/recover1")
    public Result recoverPassword1(String email, String code){
        String key=authorizeService.recover1(email,code);
        if(key.equals("邮箱验证成功")){
            return Result.success(key);
        } else {
            return  Result.error(key);
        }
    }
    @PostMapping("/recover2")
    public Result recoverPassword2(String password){
        String key=authorizeService.recover2(password);
        if(key.equals("密码修改成功")){
            return Result.success(key);
        } else {
            return  Result.error(key);
        }
    }
}
