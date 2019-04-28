package com.business.dao;

import com.business.bean.SysUser;
import org.apache.ibatis.annotations.*;

public interface UserDao {

    @Select("select id,username,nick_name,salt,password,email,phone,create_time from sys_user where username=#{username} and state = 1")
    @Results({
            @Result(column = "nick_name",property = "nickName"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "nick_name",property = "nickName")
    })
    SysUser findByUserName(@Param("username") String userName);

    @Insert("insert into sys_user (username,nick_name,password,salt,email,phone,create_time,state) " +
            " values(#{username},#{nickName},#{password},#{salt},#{email},#{phone},#{createTime},#{state})")
    int addUser(SysUser user);
}
