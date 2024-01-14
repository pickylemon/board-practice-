package com.myWebsite.www.domain;

import com.mywebsite.www.domain.PageHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class PageHandlerTest {

    @Test
    public void printTest1(){
        PageHandler ph = new PageHandler(16, 280);
        ph.print();
    }

    @Test
    public void printTest2(){
        PageHandler ph = new PageHandler(21, 380);
        ph.print();
        System.out.println(ph);

    }

}