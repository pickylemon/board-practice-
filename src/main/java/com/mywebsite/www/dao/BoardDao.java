package com.mywebsite.www.dao;

import com.mywebsite.www.domain.BoardDto;
import com.mywebsite.www.domain.PageHandler;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {
    //전체 게시글 수
    int count() throws Exception;

    //게시글 하나 읽기
    BoardDto select(Integer bno) throws Exception;
    //게시글 목록 가져오기
    List<BoardDto> selectAll() throws Exception;

    List<BoardDto> selectPage(@Param("ph") PageHandler ph) throws Exception;
    //게시글 쓰기
    int insert(BoardDto boardDto) throws Exception;

    //게시글 수정
    int update(BoardDto boardDto) throws Exception;

    //게시글 삭제(bno와 writer 정보 필요)
    int delete(@Param("bno")Integer bno, @Param("writer")String writer) throws Exception;

    //Admin용 게시글 삭제
    int deleteForAdmin(Integer bno) throws Exception;

    int deleteAll() throws Exception;

    int increaseViewCnt(Integer bno) throws Exception;
}
