package com.cloudpan.service;


import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {
     String sendValidateEmail(String email);
     String sendValidateEmail2(String email);
     String register(String username,String password,String email, String code);
     String recover1(String email,String code);
     String recover2(String password);
}
