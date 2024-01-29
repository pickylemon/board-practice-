package com.mywebsite.www.service;

import com.mywebsite.www.dao.BoardDao;
import com.mywebsite.www.domain.BoardDto;
import com.mywebsite.www.domain.PageHandler;
import com.mywebsite.www.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardDao boardDao;

    //게시물 갯수 세기
    @Override
    public int getCount() throws Exception {
        return boardDao.count();
    }

    //게시물 목록 가져오기
    @Override
    public List<BoardDto> getList() throws Exception {
        return boardDao.selectAll();
    }

    @Override
    public List<BoardDto> getPage(PageHandler ph) throws Exception {
        return boardDao.selectPage(ph);
    }
    @Override
    public List<BoardDto> getSearchPage(PageHandler ph, SearchCondition sc) throws Exception {
        return boardDao.selectSearchPage(ph, sc);
    }

    @Override
    public int getSearchCnt(SearchCondition sc) throws Exception {
        return boardDao.selectSearchCnt(sc);
    }

    //게시물 읽기
    @Override
    @Transactional(rollbackFor=Exception.class)
    public BoardDto read(Integer bno) throws Exception{
        boardDao.increaseViewCnt(bno);
        BoardDto boardDto = boardDao.select(bno);
        //게시글 읽기와 조회수 증가는 같이 묶기
        return boardDto;
    }

    //게시물 쓰기
    @Override
    public int write(BoardDto boardDto) throws Exception {
        return boardDao.insert(boardDto);
    }
//    @Override
//    public int write(BoardDto boardDto) throws Exception {
//        byte[] image = imgToByteArray(boardDto.getImage());
//        Map map = new HashMap();
//        map.put("boardDto",boardDto);
//        map.put("image", image);
//        return boardDao.insert(map);
//    }

//    public static byte[] imgToByteArray(String filePath) throws Exception {
//        byte[] img = null;
//        ByteArrayOutputStream baos = null;
//        FileInputStream fis = null;
//        try {
//            baos = new ByteArrayOutputStream();
//            fis = new FileInputStream(filePath);
//
//            byte[] buf = new byte[1024];
//            int read = 0;
//
//            while((read=fis.read(buf, 0, buf.length))!=-1){
//                baos.write(buf, 0, read);
//            }
//            img = baos.toByteArray();
//        } catch(Exception e){
//            e.printStackTrace();
//        } finally {
//            if (baos!=null)
//                baos.close();
//            if (fis!=null)
//                fis.close();
//        }
//        return img;
//    }
    //게시물 수정
    @Override
    public int modify(BoardDto boardDto) throws Exception {
        return boardDao.update(boardDto);
    }

    //게시물 삭제
    @Override
    public int remove(Integer bno, String writer) throws Exception {
        return boardDao.delete(bno, writer);
    }
    //Admin용 게시글 삭제
    @Override
    public int removeForAdmin(Integer bno) throws Exception {
        return boardDao.deleteForAdmin(bno);
    }
    //게시글 전체 삭제
    @Override
    public int removeAll() throws Exception {
        return boardDao.deleteAll();
    }

    public int increaseCommentCnt(Integer bno, Integer cnt) throws Exception {
        return boardDao.increaseCommentCnt(bno, cnt);
    }



//    private void initPk() throws Exception {
//        boardDao.initPk();
//    }
}
