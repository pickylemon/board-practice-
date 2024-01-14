package com.mywebsite.www.domain;

public class SearchCondition {
    private String title;
    private String writer;
    private String content;
    public SearchCondition(){
        this("","","");
    }
//    public SearchCondition(String title){
//        this(title, "", "");
//    }
//    public SearchCondition(String title, String content){
//        this(title, "", content);
//    }
//
//    public SearchCondition(String writer){
//        this("", writer, "");
//    }

    public SearchCondition(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
