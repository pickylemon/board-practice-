package com.mywebsite.www.dao;

import com.mywebsite.www.domain.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface UserDao {
    UserDto select(String id) throws Exception;

    List<UserDto> selectAll() throws Exception;

    int insert(UserDto userDto) throws Exception;

    int update(UserDto userDto) throws Exception;

    int count() throws Exception;

    int delete(String id, String pwd) throws Exception;

    int deleteForAdmin(String id) throws Exception;

    int deleteAll() throws Exception;


}
