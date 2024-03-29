package com.mywebsite.www.dao;

import com.mywebsite.www.domain.CommentDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDaoImpl implements CommentDao {
    @Autowired
    SqlSession session;
    private static final String namespace = "com.mywebsite.www.dao.CommentDao.";

    @Override
    public int count(Integer bno) throws Exception {
        return session.selectOne(namespace+"count", bno);
    }

    @Override
    public CommentDto select(Integer cno) throws Exception {
        return session.selectOne(namespace+"select",cno);
    }
    @Override
    public List<CommentDto> selectList(Integer bno) throws Exception {
        return session.selectList(namespace+"selectAll",bno);
    }
    @Override
    public int update(CommentDto dto) throws Exception {
        return session.update(namespace+"update",dto);
    }

    @Override
    public int delete(Integer cno, String commenter) throws Exception {
        Map map = new HashMap();
        map.put("cno",cno);
        map.put("commenter",commenter);
        return session.delete(namespace+"delete",map);
    }

    @Override
    public int deleteAll(Integer bno) throws Exception {
        return session.delete(namespace+"deleteAll", bno);
    }

    @Override
    public int insert(CommentDto dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    }

    @Override
    public int initPk() throws Exception {
        return session.update(namespace+"initPk");
    }


}
