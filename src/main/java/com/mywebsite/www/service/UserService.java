package com.mywebsite.www.service;

import com.mywebsite.www.domain.UserDto;

import java.util.List;

public interface UserService {
    //Tx 적용할 메서드를 찾지 못했다....
    int getCount() throws Exception;

    UserDto getUser(String id) throws Exception;

    List<UserDto> getList() throws Exception;

    int remove(String id, String pwd) throws Exception;

    int removeForAdmin(String id) throws Exception;

    int removeAll() throws Exception;

    int modify(UserDto userDto) throws Exception;

    int register(UserDto userDto) throws Exception;
}
