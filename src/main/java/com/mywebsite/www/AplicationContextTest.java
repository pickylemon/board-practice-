//package com.mywebsite.www;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.Enumeration;
//
//@RestController
//public class AplicationContextTest {
//
//    @Autowired
//    WebApplicationContext webApplicationContext;
//
//    @GetMapping("/apptest")
//    private void test(HttpServletRequest request) {
//        System.out.println("test start");
//        WebApplicationContext rootAc = (WebApplicationContext) request.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//
//        System.out.println("rootAc = " + rootAc);
//        System.out.println("webApplicationContext = " + webApplicationContext);
//
//        Enumeration<String> tomcatAc = request.getServletContext().getAttributeNames();
//
//        System.out.println("\n---------------------- tomcat's ServletContext --------------------");
//        while (tomcatAc.hasMoreElements()) {
//            System.out.println(tomcatAc.nextElement());
//        }
//        System.out.println("------------------------------------------------------------------\n");
//
//        System.out.println(" --------------------------- Root AC -------------------------------");
//        System.out.println(Arrays.toString(rootAc.getBeanDefinitionNames()));
//        System.out.println("------------------------------------------------------------------\n");
//
//        System.out.println("-------------------- Dispatcher Servlet's AC ---------------------- ");
//        System.out.println(Arrays.toString(webApplicationContext.getBeanDefinitionNames()));
//        System.out.println("--------------------------------------------------------------------");
//
//        System.out.println("test completed");
//    }
//}