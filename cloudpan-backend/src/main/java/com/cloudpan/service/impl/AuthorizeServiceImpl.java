package com.cloudpan.service.impl;

import com.cloudpan.entity.Account;
import com.cloudpan.mapper.UserMapper;
import com.cloudpan.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
     String from;
    @Resource
    MailSender mailSender;
    @Resource
    StringRedisTemplate template;
    @Resource
    UserMapper userMapper;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
    //发送邮件
    @Override
    public String sendValidateEmail(String email) {
        if(userMapper.findUserByUsernameOrEmail(email)!=null)
            return "此邮箱已经被使用，请使用其他邮箱注册！";
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("验证码是："+code);
        String Key = "email:" + email;
        try{
            mailSender.send(message);
            template.opsForValue().set(Key,String.valueOf(code),10, TimeUnit.MINUTES);
            return "验证码发送成功！";
        } catch (MailException e) {
            e.printStackTrace();
            return "验证码发送失败！";
        }

    }
    public String register(String username,String password,String email, String code){
        String key = "email:" + email;
        if(Boolean.TRUE.equals(template.hasKey(key))){
            String s = template.opsForValue().get(key);
            if(s==null) return "验证码失效，请重新请求";
            if(s.equals(code)) {
                if(userMapper.findUserByUsernameOrEmail(username)!=null){
                    return "此用户名已存在,请使用其他用户名";
                }
                else{
                    String pass=encoder.encode(password);
                    if(userMapper.createUser(username,pass,email)>0)
                        return "注册成功";
                    else
                        return "注册失败，请联系管理员";
                }

            } else {
                return "验证码错误，请检查后在提交";
            }
        } else {
            return "请先获取一封验证码邮件";
        }

    }
}
