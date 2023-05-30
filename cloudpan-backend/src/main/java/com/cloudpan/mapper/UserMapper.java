package com.cloudpan.mapper;

import com.cloudpan.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user_info where username = #{text} or email = #{text}")
    Account findUserByUsernameOrEmail(String text);
}
