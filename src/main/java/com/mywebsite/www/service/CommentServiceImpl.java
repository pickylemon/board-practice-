package com.mywebsite.www.service;

import com.mywebsite.www.dao.BoardDao;
import com.mywebsite.www.dao.CommentDao;
import com.mywebsite.www.domain.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
//    @Autowired
    CommentDao commentDao;
//    @Autowired
    BoardDao boardDao;

    @Autowired
    CommentServiceImpl(CommentDao commentDao, BoardDao boardDao){
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    }

    @Override
    public CommentDto read(Integer cno) throws Exception {
        return commentDao.select(cno);
    }
    @Override
    public List<CommentDto> readAll(Integer bno) throws Exception {
        return commentDao.selectList(bno);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDto commentDto) throws Exception {
        boardDao.increaseCommentCnt(commentDto.getBno(),1);
//        throw new Exception("for test");
        return commentDao.insert(commentDto);
    }

    @Override
    public int modify(CommentDto commentDto) throws Exception {
        return commentDao.update(commentDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer bno, Integer cno, String commenter) throws Exception {
        boardDao.increaseCommentCnt(bno, -1);
        return commentDao.delete(cno, commenter);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeAll(Integer bno) throws Exception {
        boardDao.setComment0(bno);
        return commentDao.deleteAll(bno);
    }


}
