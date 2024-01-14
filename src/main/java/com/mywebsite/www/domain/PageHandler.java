package com.mywebsite.www.domain;

import org.springframework.stereotype.Component;


public class PageHandler {
    Integer page; //현재 페이지
    Integer totalCnt; //총 게시글 수
    Integer pageSize = 15; //한 페이지에 보여줄 글 수
    Integer startPage; //navi의 startPage
    Integer endPage;  //navi의 endPage
    Integer naviSize = 10; //navi의 크기
    Integer totalPage;
    boolean prevPage;
    boolean nextPage;
    Integer offset;

    public PageHandler(){}
    public PageHandler(Integer page, Integer totalCnt){
        this.page = page;
        this.totalCnt = totalCnt;
        this.totalPage = (totalCnt-1) / pageSize + 1;
        this.startPage = (page-1)/naviSize*naviSize+1;
        this.endPage = Math.min(startPage + naviSize - 1, totalPage);
        this.prevPage = startPage!=1;
        this.nextPage = !endPage.equals(totalPage);
        this.offset = (page-1)*pageSize;
    }

    public void print(){
        System.out.println("page = " + page);
        if(prevPage){
            System.out.print("<< ");
        }
        for(int i=startPage; i <= endPage; i++){
            System.out.print(i+" ");
        }
        if(nextPage){
            System.out.println(">>");
        }
        System.out.println();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public Integer getNaviSize() {
        return naviSize;
    }

    public void setNaviSize(Integer naviSize) {
        this.naviSize = naviSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isPrevPage() {
        return prevPage;
    }

    public void setPrevPage(boolean prevPage) {
        this.prevPage = prevPage;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "page=" + page +
                ", totalCnt=" + totalCnt +
                ", pageSize=" + pageSize +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", prevPage=" + prevPage +
                ", nextPage=" + nextPage +
                ", offset=" + offset +
                '}';
    }
}
