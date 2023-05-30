package com.cloudpan.service;

import com.cloudpan.entity.Account;
import com.cloudpan.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthorizeService implements UserDetailsService {
    @Resource
    UserMapper userMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        if(username == null)
            throw new UsernameNotFoundException("用户名不能为空");
        Account account = userMapper.findUserByUsernameOrEmail(username);
        if(account == null)
            throw new UsernameNotFoundException("用户名或密码错误");


        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("users")
                .build();
    }
}
