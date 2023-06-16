package com.cloudpan.controller;

import com.cloudpan.entity.Result;
import com.cloudpan.entity.UserAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/me")
    public Result me(HttpSession session){
        System.out.println("2:"+session.getId());
        UserAccount user = (UserAccount) session.getAttribute("account");
        return Result.success(user);
    }
}
