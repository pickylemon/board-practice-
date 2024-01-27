package com.myWebsite.www.domain;

import com.mywebsite.www.domain.PageHandler;
import com.mywebsite.www.domain.SearchCondition;
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
        SearchCondition sc = new SearchCondition("T","안녕",16);
        PageHandler ph = new PageHandler(sc, 280);
        ph.print();
    }

    @Test
    public void printTest2(){
        SearchCondition sc = new SearchCondition("T","안녕",21);
        PageHandler ph = new PageHandler(sc, 380);
        ph.print();
        System.out.println(ph);

    }

}