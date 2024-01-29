package com.mywebsite.www.service;

import com.mywebsite.www.domain.CommentDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    CommentDto read(Integer cno) throws Exception;

    List<CommentDto> readAll(Integer bno) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    int write(CommentDto commentDto) throws Exception;

    int modify(CommentDto commentDto) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    int remove(Integer bno, Integer cno, String commenter) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    int removeAll(Integer bno) throws Exception;
}
