package com.mywebsite.www.controller;

import com.mywebsite.www.domain.CommentDto;
import com.mywebsite.www.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
@RequestMapping("/comments")
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/write")
    public ResponseEntity<String> writeComment(HttpSession session, Integer bno, @RequestBody CommentDto commentDto){
//        System.out.println("commentDto = " + commentDto);
//        System.out.println("bno = " + bno);
        String commenter = (String)session.getAttribute("id");
        System.out.println("commenter = " + commenter);
        commentDto.setCommenter(commenter);
        commentDto.setBno(bno);
        System.out.println("commentDto = " + commentDto);
//        commentDto.setBno(bno);
//        System.out.println("commentDto = " + commentDto);
//        System.out.println("bno = " + bno);
        try {
            int rowCnt = commentService.write(commentDto);
            System.out.println("rowCnt = " + rowCnt);
            if(rowCnt!=1) {
                throw new Exception("WRT comment failed");
            }
            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read")
    public ResponseEntity<List<CommentDto>> getCommentList(Integer bno){
        try {
            List<CommentDto> commentList = commentService.readAll(bno);
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
