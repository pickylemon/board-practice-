package com.myWebsite.www.dao;

import com.mywebsite.www.dao.UserDao;
import com.mywebsite.www.domain.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDaoImplTest {
    @Autowired
    UserDao userDao;

    @Test
    public void deleteUser() throws Exception {
        userDao.deleteAll();
        assertTrue(userDao.count()==0);
        UserDto userDto = new UserDto("abc","1234","강아지","abc@abc.com");
        int rowCnt = userDao.insert(userDto);
        assertTrue(rowCnt==1);
        UserDto userDto2 = userDao.select(userDto.getId());
        assertTrue(userDto2!=null);
        assertTrue(userDto.getId().equals(userDto2.getId()));
        rowCnt = userDao.delete(userDto2.getId(),userDto2.getPwd());
        assertTrue(rowCnt==1);
        assertTrue(userDao.count()==0);
    }

    @Test
    public void insertUser() throws Exception {
        String[] names = {"강아지","고양이","앵무새","돌고래","다람쥐","부엉이"};
        String[] emails = {"aaa@aaa.com", "bbb@bbb.com", "ccc@ccc.com", "ddd@ddd.com", "eee@eee.com"};
        userDao.deleteAll();
        assertTrue(userDao.count()==0);
        for(int i=1; i<=10; i++){
            UserDto userDto = new UserDto("asdf"+i,"1234",getRandomValue(names),getRandomValue(emails));
            userDao.insert(userDto);
        }
        assertTrue(userDao.count()==10);
        List<UserDto> list = userDao.selectAll();
        System.out.println("list = " + list);
    }


    private String getRandomValue(String[] strArr){
        int idx = (int)(Math.random()*strArr.length);
        return strArr[idx];
    }

}