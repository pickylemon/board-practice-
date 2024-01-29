package com.mywebsite.www.dao;

import com.mywebsite.www.domain.CommentDto;

import java.util.List;

public interface CommentDao {
    int count(Integer bno) throws Exception;
    CommentDto select(Integer cno) throws Exception;

    List<CommentDto> selectList(Integer bno) throws Exception;

    int update(CommentDto dto) throws Exception;

    int delete(Integer cno, String commenter) throws Exception;

    int deleteAll(Integer bno) throws Exception; //해당 bno의 모든 댓글 지우기

    int insert(CommentDto dto) throws Exception;

    int initPk() throws Exception;

}
