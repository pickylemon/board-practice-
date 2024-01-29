//package com.myWebsite.www.service;
//
//import com.mywebsite.www.dao.BoardDao;
//import com.mywebsite.www.domain.BoardDto;
//import com.mywebsite.www.domain.PageHandler;
//import com.mywebsite.www.domain.SearchCondition;
//import com.mywebsite.www.service.BoardService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
//public class BoardServiceImplTest {
//    @Autowired
//    BoardService boardService;
//    @Autowired
//    BoardDao boardDao;
//
//    @Test //일부러 increaseViewCnt에 예외 발생시켜서 Tx 정상작동하는지 보기(전체 rollback)
//    public void readTest3() throws Exception {
//        boardDao.deleteAll(); //매 테스트마다 같은 결과가 나올 수 있도록 초기화
//        assertTrue(boardDao.count()==0);
//        BoardDto boardDto = new BoardDto("질문드립니다","asdfadjfljqewfadfadfadf","고양이");
//        int rowCnt = boardDao.insert(boardDto);
//        assertTrue(rowCnt==1); //insert성공
//        BoardDto boardDto2 = boardDao.selectAll().get(0); //DB에 넣은 게시물 꺼내오기
//
//        try {
//            BoardDto boardDto3 = boardService.read(boardDto2.getBno());
//            //근데 service에서 Tx묶인애들 자체 try-catch에서 rollback처리 되는데 왜 에러가 나지?
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            BoardDto boardDto4 = boardDao.select(boardDto2.getBno());
//            assertTrue(boardDto4.getView_cnt()==0);
//            System.out.println("boardDto4.getView_cnt() = " + boardDto4.getView_cnt());
//            //어쨌든, 1. 읽어오기 실패 2. 조회수 증가 안함 모두 확인함.
//        }
////        assertTrue(boardDto3==null); //전체 rollback되어서 읽어온 게시물이 없어야 한다.
//
//
//
//
//
//
//    }
//
//    @Test
//    public void readTest2() throws Exception {
//        boardDao.deleteAll();
//        BoardDto boardDto = new BoardDto("질문드립니다","asdfadjfljqewfadfadfadf","고양이");
//        int rowCnt = boardDao.insert(boardDto);
//        assertTrue(rowCnt==1); //insert성공
//        BoardDto boardDto2 = boardDao.selectAll().get(0); //DB에 넣은 게시물 꺼내오기
//        boardService.read(boardDto2.getBno());
//
//        BoardDto boardDto3 = boardDao.select(boardDto2.getBno());
//        assertTrue(boardDto3!=null);
//        assertTrue(boardDto3.getView_cnt()==1);
//    }
//    @Test
//    public void readTest() throws Exception {
//        boardDao.deleteAll();
//        BoardDto boardDto = new BoardDto("질문드립니다","asdfadjfljqewfadfadfadf","고양이");
//        int rowCnt = boardDao.insert(boardDto);
//        assertTrue(rowCnt==1); //insert성공
//        BoardDto boardDto2 = boardDao.selectAll().get(0); //DB에 넣은 게시물 꺼내오기
//        BoardDto boardDto3 = boardService.read(boardDto2.getBno());
//        //그 bno로 게시물 가져오기(게시물을 못 가져오거나 view_cnt update못하면 rollback해서 전체 취소)
//
//        System.out.println("boardDto3 = " + boardDto3); //전체 취소되면 null이 나옴..
//        System.out.println(boardDto3.getView_cnt()==1);
//
//        //근데 왜 view_cnt가 1이 안나오지
//        //아! service에서 내가 boardDto를 먼저 꺼내온 다음에 view_cnt를 증가시켰으니까.
//        //service에서 두 메서드의 순서를 바꿔주면 해결됨.
////
////        BoardDto boardDto4 = boardService.read(boardDto2.getBno());
////        System.out.println("boardDto4 = " + boardDto4); //얘는 정상적으로 viewCnt가 증가해있다.
//
//    }
//
//    @Test
//    public void getPageTest() throws Exception {
//        boardDao.deleteAll();
//        for(int i=0; i<200; i++){
//            BoardDto boardDto = new BoardDto("안녕"+i,"ㅁㄴㅇㄻㄴㅇㄹ","asdf"+i/10);
//            boardDao.insert(boardDto);
//        }
//    }
//
//    @Test
//    public void getSearchCnt() throws Exception {
//        boardDao.deleteAll();
////        boardDao.initPk();
//        for(int i=0; i<200; i++){
//            BoardDto boardDto = new BoardDto("안녕"+i,"ㅁㄴㅇㄻㄴㅇㄹ","asdf"+i/10);
//            boardDao.insert(boardDto);
//        }
//        SearchCondition sc = new SearchCondition("T","안녕1",1);
//        System.out.println("boardDao.selectSearchCnt(sc) = " + boardDao.selectSearchCnt(sc));
//    }
//
//    @Test
//    public void getSearchPage() throws Exception {
////        boardDao.deleteAll();
////        boardDao.initPk();
////        for(int i=0; i<200; i++){
////            BoardDto boardDto = new BoardDto("안녕"+i,"ㅁㄴㅇㄻㄴㅇㄹ","asdf"+i/10);
////            boardDao.insert(boardDto);
////        }
//        SearchCondition sc = new SearchCondition("W","asdf1",1);
//        int cnt = boardDao.selectSearchCnt(sc);
//        PageHandler ph = new PageHandler(sc, cnt);
//        List<BoardDto> list = boardDao.selectSearchPage(ph,sc);
//        System.out.println("cnt = " + cnt);
//        System.out.println("list = " + list);
//        //아 진짜 왜 안되는거지?
//    }
//
//}