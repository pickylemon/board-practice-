package com.mywebsite.www.service;

import com.mywebsite.www.dao.UserDao;
import com.mywebsite.www.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    //Tx 적용할 메서드를 찾지 못했다....
    @Override
    public int getCount() throws Exception{
        return userDao.count();
    }
    @Override
    public UserDto getUser(String id) throws Exception{
        return userDao.select(id);
    }
    @Override
    public List<UserDto> getList() throws Exception{
        return userDao.selectAll();
    }
    @Override
    public int remove(String id, String pwd) throws Exception{
        return userDao.delete(id, pwd);
    }
    @Override
    public int removeForAdmin(String id) throws Exception{
        return userDao.deleteForAdmin(id);
    }
    @Override
    public int removeAll() throws Exception{
        return userDao.deleteAll();
    }
    @Override
    public int modify(UserDto userDto) throws Exception{
        return userDao.update(userDto);
    }
    @Override
    public int register(UserDto userDto) throws Exception{
        return userDao.insert(userDto);
    }







}
