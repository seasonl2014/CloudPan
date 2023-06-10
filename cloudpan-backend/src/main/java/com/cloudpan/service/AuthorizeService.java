package com.cloudpan.service;


import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {
     String sendValidateEmail(String email);
     String register(String username,String password,String email, String code);
}
