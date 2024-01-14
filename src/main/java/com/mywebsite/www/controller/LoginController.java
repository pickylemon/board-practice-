package com.mywebsite.www.controller;

import com.mywebsite.www.domain.UserDto;
import com.mywebsite.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserService userService;

    //LoginController에서 하는 일 ->

    //3.로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes rattr) throws Exception {
        session.invalidate();
        String msg ="정상적으로 로그아웃 되었습니다.";
        rattr.addFlashAttribute("msg",msg);
        return "redirect:/";
    }
    //1. 로그인 페이지 보여주기
    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }
    //2. 로그인 검증(Post)
    @PostMapping("/login")
    public String login(String toURL, HttpServletRequest request, HttpServletResponse response, HttpSession session, UserDto userDto, RedirectAttributes rattr, boolean rememberMe){
        if(!loginCheck(userDto)){
            //로그인 검증에 실패
            //loginForm으로 redirect
            return "redirect:/login/login";
        }
        //로그인 성공시
        //1. 세션에 아이디 저장
        session.setAttribute("id",userDto.getId());
        //2. rememberMe가 체크 되어 있으면 Cookie 만들어서 저장
        Cookie cookie;
        if(rememberMe){ //아이디 저장 체크하면 쿠키 생성 & 아이디 저장
            cookie = new Cookie("id",userDto.getId());
            cookie.setMaxAge(60*60*24);
        } else { //아이디 저장 체크 해제시 쿠키 삭제
            cookie = new Cookie("id","");
            cookie.setMaxAge(0);
        }
        response.addCookie(cookie);
        System.out.println("toURL = " + toURL); //이게 왜 null이지 잃어버렸나?
        return "redirect:"+(toURL==null||toURL.equals("")? "/" : toURL); //로그인 성공시 홈으로 이동.
    }

    private boolean loginCheck(UserDto userDto){
        UserDto userDto2 = null;
        try {
            userDto2 = userService.getUser(userDto.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (userDto2 == null) return false;
//        return userDto.getId().equals(userDto2.getId()) && userDto.getPwd().equals(userDto2.getPwd());
        return userDto2!=null && userDto.getPwd().equals(userDto2.getPwd());
    }

}

