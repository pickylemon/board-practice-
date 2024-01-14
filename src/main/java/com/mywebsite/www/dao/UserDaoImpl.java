//package com.mywebsite.www.dao;
//
//import com.mywebsite.www.domain.UserDto;
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class UserDaoImpl implements UserDao {
//    @Autowired
//    SqlSession session;
//
//    private static String namespace = "com.mywebsite.www.dao.UserDao.";
//    //Mapper의 namespace와는 달리 DAO의 namespace에는 마지막에 .을 찍어준다.
//
//    @Override
//    public UserDto select(String id) throws Exception{
//        return session.selectOne(namespace+"select",id);
//    }
//    @Override
//    public List<UserDto> selectAll() throws Exception{
//        return session.selectList(namespace+"selectAll");
//    }
//    @Override
//    public int insert(UserDto userDto) throws Exception{
//        return session.insert(namespace+"insert",userDto);
//    }
//    @Override
//    public int update(UserDto userDto) throws Exception{
//        return session.update(namespace+"update",userDto);
//    }
//    @Override
//    public int count() throws Exception{
//        return session.selectOne(namespace+"count");
//    }
//    @Override
//    public int delete(String id, String pwd) throws Exception{
//        Map map = new HashMap();
//        map.put("id",id);
//        map.put("pwd",pwd);
//        return session.delete(namespace+"delete",map);
//    }
//    @Override
//    public int deleteForAdmin(String id) throws Exception{
//        return session.delete(namespace+"delete",id);
//    }
//    @Override
//    public int deleteAll() throws Exception{
//        return session.delete(namespace+"deleteAll");
//    }
//}
