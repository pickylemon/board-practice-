package com.myWebsite.www.dao;

import com.mywebsite.www.dao.BoardDao;
import com.mywebsite.www.domain.BoardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDaoImplTest {
    @Autowired
    BoardDao boardDao;

    @Test
    public void count() throws Exception {
        assertTrue(boardDao!=null);
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);
        String[] wrtArr = {"강아지","고양이","앵무새","호랑이","다람쥐"};
        for(int i=0; i<20; i++){
            BoardDto boardDto = new BoardDto("안녕하세요"+i,"asdfasdfasdf",getRandom(wrtArr));
            assertTrue(boardDao.insert(boardDto)==1);
        }
        assertTrue(boardDao.count()==20);
    }

    @Test
    public void select() throws Exception {
        assertTrue(boardDao!=null);
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);
        BoardDto boardDto = new BoardDto("질문드립니다","asdfadjfljqewfadfadfadf","고양이");
        assertTrue(boardDao.insert(boardDto)==1);
        System.out.println("boardDto = " + boardDto);
        // 이 자바 객체는 DB에서 꺼내온 게 아니다. DB에는 bno 21로 들어갔지만 여기서 boardDto의 bno는 null이다(Integer)
        // BoardDto boardDto2 = boardDao.select(boardDto.getBno()); // 해당 bno로 게시물 검색해서 게시물을 얻는다.
        // 그래서 이 코드는 select에 null을 집어넣은 꼴. DB에서 직접 데이터 확인해가며 bno를 찾아야 할듯.
        // or bno정보 없이 dto를 받아와서 비교.

        BoardDto boardDto2 = boardDao.selectAll().get(0);
        System.out.println("boardDto2 = " + boardDto2);
        assertTrue(boardDto.getWriter().equals(boardDto2.getWriter()));
        //DB에 넣은 dto와 꺼낸 dto가 같은지 체크함(

//        System.out.println("boardDto2 = " + boardDto2);
//        assertTrue(boardDto.getWriter().equals(boardDto2.getWriter()));
//        System.out.println("boardDto = " + boardDto);
//        System.out.println("boardDto2 = " + boardDto2);
//        assertTrue(boardDto.equals(boardDto2));
    }

    @Test
    public void selectAll() throws Exception {
        assertTrue(boardDao!=null); //DAO가 잘 주입되었는지 확인
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);
//        String[] wrtArr = {"강아지","고양이","앵무새","호랑이","다람쥐"};
        for(int i=0; i<20; i++){
            BoardDto boardDto = new BoardDto("안녕하세요"+i,"asdfasdfasdf","asdf"+((int)(Math.random()*20)+1));
            assertTrue(boardDao.insert(boardDto)==1);
        }
        assertTrue(boardDao.count()==20);
        List<BoardDto> list = boardDao.selectAll();
        System.out.println("list = " + list);
        assertTrue(list.size()==20); //꺼내온 dto가 20개인지 확인하기
    }

    @Test
    public void insert() throws Exception{
        assertTrue(boardDao!=null);
        boardDao.deleteAll();
        String[] wrtArr = {"강아지","고양이","앵무새","호랑이","다람쥐"};
        for(int i=0; i<20; i++){
            BoardDto boardDto = new BoardDto("안녕하세요"+i,"asdfasdfasdf",getRandom(wrtArr));
            assertTrue(boardDao.insert(boardDto)==1);
            assertTrue(boardDao.count()==i+1);
        }

        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);
        BoardDto boardDto = new BoardDto("반갑습니다","야옹야옹","호랑이");
        int rowCnt = boardDao.insert(boardDto);
        assertTrue(rowCnt==1);
    }

    @Test
    public void update() throws Exception {
        boardDao.deleteAll();
        BoardDto boardDto = new BoardDto("안녕하세요","안녕히가세요","돌고래");
        int rowCnt = boardDao.insert(boardDto);
        assertTrue(rowCnt==1);
        //boardDto.setContent("반갑습니다");
        //rowCnt = boardDao.update(boardDto);
        //rowCnt가 0이 나와서 안된다. 왜 0이 나오는가?
        //update는 bno와 writer를 모두 비교하는데, 우리가 만들어준 dto는 bno가 null이라서
        //update실패하는 것

        BoardDto boardDto2 = boardDao.selectAll().get(0);
        System.out.println("boardDto2 = " + boardDto2);

        boardDto2.setContent("반갑습니다");
        rowCnt = boardDao.update(boardDto2);
        assertTrue(rowCnt==1);

        BoardDto boardDto3 = boardDao.selectAll().get(0);
        assertTrue(boardDto2.equals(boardDto3));
        //boardDto2와 boardDto3는 자바코드로 만든게 아니라 DB에서 꺼내온 애들이라
        //equals로 비교 가능

    }

    @Test
    public void delete() throws Exception {
        boardDao.deleteAll();
        BoardDto boardDto = new BoardDto("감사합니다","안녕하세요","강아지");
        int rowCnt = boardDao.insert(boardDto);
        assertTrue(rowCnt==1);
        BoardDto boardDto2 = boardDao.selectAll().get(0);


        rowCnt = boardDao.delete(boardDto2.getBno(), boardDto2.getWriter());
        assertTrue(rowCnt==1);
        assertTrue(boardDao.count()==0);

    }

    @Test
    public void deleteForAdmin() throws Exception {
        boardDao.deleteAll();
        BoardDto boardDto = new BoardDto("감사합니다","안녕하세요","강아지");
        int rowCnt = boardDao.insert(boardDto);
        assertTrue(rowCnt==1);
        BoardDto boardDto2 = boardDao.selectAll().get(0);
        System.out.println("boardDto2 = " + boardDto2);
        System.out.println("boardDto2.getBno() = " + boardDto2.getBno());
        rowCnt = boardDao.deleteForAdmin(boardDto2.getBno());
        assertTrue(rowCnt==1);
    }

    @Test
    public void deleteAll() throws Exception {
        boardDao.deleteAll();
        String[] wrtArr = {"강아지","고양이","앵무새","호랑이","다람쥐"};
        for(int i=0; i<20; i++){
            BoardDto boardDto = new BoardDto("안녕하세요"+i,"asdfasdfasdf",getRandom(wrtArr));
            assertTrue(boardDao.insert(boardDto)==1);
            assertTrue(boardDao.count()==i+1);
        }
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0); //다 지웠으니까 0개
        assertTrue(boardDao.deleteAll()==0);
        //이미 비어있는 상태니까 삭제할게 없어서 영향받은 row는 0개


    }

    @Test
    public void increaseViewCnt() throws Exception {
        boardDao.deleteAll();
        BoardDto boardDto = new BoardDto("감사합니다", "안녕하세요", "강아지");
        int rowCnt = boardDao.insert(boardDto);
        assertTrue(rowCnt == 1);
        BoardDto boardDto2 = boardDao.selectAll().get(0);
        rowCnt = boardDao.increaseViewCnt(boardDto2.getBno());
        assertTrue(rowCnt == 1);
        BoardDto boardDto3 = boardDao.select(boardDto2.getBno());
        assertTrue(boardDto3.getView_cnt() == 1);
    }

    private String getRandom(String[] strArr){
        int idx = (int)(Math.random()*strArr.length);
        return strArr[idx];
    }
}