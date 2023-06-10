package com.cloudpan.mapper;

import com.cloudpan.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user_info where username = #{text} or email = #{text}")
    Account findUserByUsernameOrEmail(String text);
    @Insert("insert into user_info (username,email,password) values (#{username},#{email},#{password})" )
    int createUser(@Param("username") String username, @Param("password") String password, @Param("email") String email);
}
