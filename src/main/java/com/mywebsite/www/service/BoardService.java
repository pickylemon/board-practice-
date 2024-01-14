package com.mywebsite.www.service;

import com.mywebsite.www.domain.BoardDto;
import com.mywebsite.www.domain.PageHandler;
import com.mywebsite.www.domain.SearchCondition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {
    //게시물 갯수 세기
    int getCount() throws Exception;

    //전체 게시물 목록 가져오기
    List<BoardDto> getList() throws Exception;

    //한 페이지 가져오기
    List<BoardDto> getPage(PageHandler ph) throws Exception;

    List<BoardDto> getSearchPage(PageHandler ph, SearchCondition sc) throws Exception;
    int getSearchCnt(SearchCondition sc) throws Exception;

    //게시물 읽기
    @Transactional(rollbackFor = Exception.class)
    BoardDto read(Integer bno) throws Exception;

    //게시물 쓰기
    int write(BoardDto boardDto) throws Exception;

    //게시물 수정
    int modify(BoardDto boardDto) throws Exception;

    //게시물 삭제
    int remove(Integer bno, String writer) throws Exception;

    //Admin용 게시글 삭제
    int removeForAdmin(Integer bno) throws Exception;

    //게시글 전체 삭제
    int removeAll() throws Exception;
}
