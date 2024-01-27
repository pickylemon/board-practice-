package com.mywebsite.www.dao;

import com.mywebsite.www.domain.BoardDto;
import com.mywebsite.www.domain.PageHandler;
import com.mywebsite.www.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository //Bean으로 등록되어 @Autowired 쓸 수 있게
public class BoardDaoImpl implements BoardDao {
    @Autowired
    SqlSession session;

    private static final String namespace = "com.mywebsite.www.dao.BoardDao.";

    //전체 게시글 수
    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    }

    //게시글 하나 읽기
    @Override
    public BoardDto select(Integer bno) throws Exception{
        return session.selectOne(namespace+"select",bno);
    }
    //게시글 목록 가져오기
    @Override
    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    }

    @Override
    public List<BoardDto> selectPage(PageHandler ph) throws Exception {
        return session.selectList(namespace+"selectPage", ph);
    }

    @Override
    public List<BoardDto> selectSearchPage(PageHandler ph, SearchCondition sc) throws Exception {
        Map map = new HashMap();
//        System.out.println("ph = " + ph);
//        System.out.println(sc);
        map.put("option",sc.getOption());
        map.put("pageSize",ph.getPageSize());
        map.put("offset",ph.getOffset());
        map.put("keyword",sc.getKeyword());
        return session.selectList(namespace+"selectSearchPage", map);
    }

    @Override
    public int selectSearchCnt(SearchCondition sc) throws Exception {
        return session.selectOne(namespace+"selectSearchCnt", sc);
    }

    //게시글 쓰기
    @Override
    public int insert(BoardDto boardDto) throws Exception {
        return session.insert(namespace+"insert", boardDto);
    }

    //게시글 수정
    @Override
    public int update(BoardDto boardDto) throws Exception {
        return session.update(namespace+"update", boardDto);
    }

    //게시글 삭제(bno와 writer 정보 필요)
    @Override
    public int delete(Integer bno, String writer) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("bno",bno);
        map.put("writer",writer);
        return session.delete(namespace+"delete",map);
    }

    //Admin용 게시글 삭제
    @Override
    public int deleteForAdmin(Integer bno) throws Exception {
        return session.delete(namespace+"deleteForAdmin", bno);
    }

    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace+"deleteAll");
    }

    //조회수 증가는 bno를 필요로 한다(해당 게시물에 대한 조회수 증가)
    @Override
    public int increaseViewCnt(Integer bno) throws Exception {
//        throw new Exception("Tx test");
        return session.update(namespace+"increaseViewCnt", bno);
    }

//    @Override
//    public void initPk() throws Exception {
//        return session.
//    }
}
