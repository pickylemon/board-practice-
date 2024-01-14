//package com.mywebsite.www.controller;
//
//import com.mywebsite.www.domain.Person;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class SimpleRestController {
////    @GetMapping("/ajax")
////    public String ajax(){
////        return "ajax";
////    }
//
//    @GetMapping("/test")
//    public String test(){
//        return "test";
//    }
//
//    @PostMapping("/send")
//    @ResponseBody
//    public Person test(@RequestBody Person p){
//        System.out.println("p = " + p);
//        p.setName("ABC");
//        p.setAge(p.getAge() + 10);
//
//        return p; //MVC 패턴에서와 달리 view이름이 아니라 객체 반환
//    }
//}
