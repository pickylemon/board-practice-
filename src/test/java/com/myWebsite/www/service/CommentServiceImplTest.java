package com.myWebsite.www.service;

import com.mywebsite.www.dao.BoardDao;
import com.mywebsite.www.dao.CommentDao;
import com.mywebsite.www.domain.CommentDto;
import com.mywebsite.www.service.CommentService;
import com.mywebsite.www.service.CommentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class CommentServiceImplTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentDao commentDao;
    @Autowired
    BoardDao boardDao;

    @Test
    public void insertTest() throws Exception{
        commentService.removeAll(1);
        commentDao.initPk();
        int cnt = 0;
        int rowCnt;
        for(int i=1; i<=50; i++){
            CommentDto dto = new CommentDto("hi there"+i, "asdf"+i);
            dto.setBno(1);
            dto.setPcno(1);
            rowCnt = commentService.write(dto);
            cnt += rowCnt;
        }
        assertTrue(cnt==50);
        assertTrue(commentDao.count(1)==cnt);
    }

    @Test
    public void removeTest() throws Exception{
//        commentDao.deleteAll(1);
        commentService.removeAll(1);
        commentDao.initPk();
        assertTrue(commentDao.count(1)==0);
        int cnt = 0;
        for(int i=1; i<=50; i++){
            CommentDto dto = new CommentDto("hi there"+i, "asdf"+i);
            dto.setBno(1);
            dto.setPcno(1);
            cnt += commentService.write(dto);
        }
        assertTrue(cnt==50);
        assertTrue(commentDao.count(1)==cnt);

        for(int i=1; i<=50; i++){
            commentService.remove(1,i,"asdf"+i);
        }
//        assertTrue(commentDao.count(1)==0);
    }

    @Test
    public void updateTest() throws Exception {
        commentService.removeAll(1);
        commentDao.initPk();
        assertTrue(commentDao.count(1)==0);
        CommentDto dto = new CommentDto("asdfasdf","qwer");
        dto.setBno(1);
        dto.setPcno(1);
        int rowCnt = commentService.write(dto);
        assertTrue(rowCnt==1);
        CommentDto dto2 = commentService.readAll(1).get(0);
        System.out.println("dto2 = " + dto2);
        CommentDto dto3 = commentService.read(dto2.getCno());
        assertTrue(dto2.equals(dto3));
//        assertTrue(dto.equals(dto2)); //dto는 자바객체. 아직 DB에 들어가기 전이라 cno가 null인 상태

        dto2.setComment("qwerqwer");
        rowCnt = commentService.modify(dto2);
        assertTrue(rowCnt==1);
        assertTrue(commentService.readAll(1).get(0).getCno().equals(dto2.getCno()));
    }
}