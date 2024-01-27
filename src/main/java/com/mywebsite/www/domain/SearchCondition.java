package com.mywebsite.www.domain;

import org.springframework.web.util.UriComponentsBuilder;

public class SearchCondition {
    private Integer page=1;
    private String keyword="";
    private String option="A";

//    public SearchCondition(){
//        this("A","", 1);
//    }
    public SearchCondition(String option, String keyword, Integer page) {
        this.option = option;
        this.keyword = keyword;
        this.page = page;
        System.out.println("getQueryParam() = " + getQueryParam());
    }
    public SearchCondition(){}


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "page=" + page +
                ", keyword='" + keyword + '\'' +
                ", option='" + option + '\'' +
                '}';
    }

    public String getQueryParam(){
        return getQueryParam(getPage());
    }
    public String getQueryParam(Integer page){
        return UriComponentsBuilder.newInstance()
                .queryParam("page",page)
                .queryParam("option",getOption())
                .queryParam("keyword",getKeyword())
                .build()
                .toString();
    }

}
