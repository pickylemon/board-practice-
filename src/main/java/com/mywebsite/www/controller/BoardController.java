package com.mywebsite.www.controller;

import com.mywebsite.www.domain.BoardDto;
import com.mywebsite.www.domain.PageHandler;
import com.mywebsite.www.domain.SearchCondition;
import com.mywebsite.www.domain.UserDto;
import com.mywebsite.www.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("/boardList")
    public String myBoard(Integer page, HttpServletRequest request, HttpSession session, Model m, RedirectAttributes rattr) {
        //로그인 여부 먼저 검사해서 로그인 안되어 있으면 로그인 페이지로 넘기기
        System.out.println("[boardList] page = " + page);
        if(page==null) page=1;
        if(session.getAttribute("id")==null){
            String msg = "로그인이 필요한 페이지입니다.";
            String toURL = request.getRequestURL().toString(); //toURI하면 redirect시 context root가 중복된다.
            rattr.addFlashAttribute("msg",msg);
            rattr.addAttribute("toURL",toURL);
            return "redirect:/login/login";
        }
        try {
            int totalCnt = boardService.getCount();
            System.out.println("page = " + page);
            System.out.println("totalCnt = " + totalCnt);
            PageHandler ph = new PageHandler(page,totalCnt);
//            System.out.println("ph = " + ph);
            List<BoardDto> list = boardService.getPage(ph);
//            System.out.println("list = " + list);
            m.addAttribute("ph",ph);
            m.addAttribute("list",list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "myBoard";
    }

    @PostMapping("/search")
    public String getSearchPage(Integer page, String search, Model m) {
        //searchForm에서 넘어온 매개변수로 boardService 메서드 호출
        System.out.println("[search] page = " + page);
        if(page==null) page=1;
        try {
            SearchCondition sc = new SearchCondition(search,"",""); //제목으로만 검색
            int totalCnt = boardService.getSearchCnt(sc);
            PageHandler ph = new PageHandler(page,totalCnt);
            List<BoardDto> list = boardService.getSearchPage(ph, sc);
            m.addAttribute("ph",ph);
            m.addAttribute("list",list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "myBoard";

    }
    @GetMapping("/read")
    public String myPost(Integer page, Integer bno, Model m, RedirectAttributes rattr){
        try {
            System.out.println("[read] page = " + page);
            System.out.println("bno = " + bno);
            BoardDto boardDto = boardService.read(bno);
            m.addAttribute("page",page); //myPost로 전달이 안된다. 그냥 page로 적으면 안됨. 숫자가 들어가는거라서.
            m.addAttribute(boardDto);
            return "myPost";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg","READ_FAIL");
            m.addAttribute(page);
            return "myBoard";
        }
    }

    @GetMapping("/delete") // form으로부터 boardDto내용을 받아와야 해서 Post로 요청받음.
    public String delete(Integer page, Integer bno, HttpServletRequest request, RedirectAttributes rattr, Model m, HttpSession session) throws Exception{
        String writer = (String)session.getAttribute("id");
//        String writer = "강아지";
        System.out.println("bno = " + bno);
        System.out.println(writer);
        Enumeration paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()){
            System.out.println("paramNames.nextElement() = " + paramNames.nextElement());
        } //param으로 넘어온 value들 확인해봄. UserDto라고 바로 넣어주면 읽어오질 못하는 것 같다. 왤까.???
        //작성자와 현재 로그인 한 회원이 같아야 삭제 가능.
        try {
            int rowCnt = boardService.remove(bno,writer);
            System.out.println("rowCnt = " + rowCnt);
            if(rowCnt==1){ //삭제 성공시
                System.out.println("rowCnt = " + rowCnt);
                rattr.addAttribute("page",page);
                rattr.addFlashAttribute("msg","DEL_OK");
                return "redirect:/board/boardList";
            }
            //삭제 실패시 삭제 실패 메시지 띄우고 원래 내용을 보여준다.
            //그냥 myPost로만 가면 boardDto가 없으니까 빈화면이 나온다.
            m.addAttribute("msg","DEL_FAIL");
            BoardDto boardDto = boardService.read(bno);
            m.addAttribute("boardDto",boardDto);
            return "myPost"; //그냥 가면 안되고 dto같이 가야 화면에 뿌려질 수 있다.
            //form에서 boardDto를 받아와서 그대로 넘겨주고 싶은데, boardDto를 매개변수로 쓰면
            //Controller까지 못오는 것 같아서, bno로 boardDto를 새로 읽어서 넘겨주기.
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    @PostMapping("/modify")
    public String modify(Integer page, HttpSession session, BoardDto boardDto, BindingResult result, RedirectAttributes rattr, Model m) throws Exception {
        // 자꾸 dto가 안들어가길래 bindingresult붙여봄. ...슬래시를 따옴표 안에 포함시켜서 난 에러...,,,맨날 오타 내는구나,,,,,,,,,
        System.out.println("result = " + result);
        System.out.println("boardDto = " + boardDto);

        try {
            String writer=(String)session.getAttribute("id");
            boardDto.setWriter(writer);
            //현재 로그인 회원과 게시글 작성자가 동일해야 update 성공.
            //사실 수정, 삭제 버튼은 게시글 작성자와 로그인 회원이 일치할때만 보이는 버튼이어야 맞다.

            int rowCnt = boardService.modify(boardDto);
            if(rowCnt == 1){
                rattr.addAttribute("page",page);
                rattr.addFlashAttribute("msg","MOD_OK");
                return "redirect:/board/boardList";
            } else {
                m.addAttribute(boardDto);
                m.addAttribute("msg","MOD_FAIL");
                return "myPost";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e; //근데 예외처리 어떻게 하지? 자꾸 이렇게 던지면 안되잖아. controller인데 또 어디다가 던져........
        }
    }

    @GetMapping("/write")
    public String write(){
        return "myPostWrite";
    }


    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m, RedirectAttributes rattr) throws Exception {
        try {
            int rowCnt = boardService.write(boardDto);
            if(rowCnt==1){
                rattr.addFlashAttribute("msg","WRT_OK");
                return "redirect:/board/boardList";
            } else{
                m.addAttribute("msg","WRT_FAIL");
                m.addAttribute(boardDto);
                return "myPostWrite";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
