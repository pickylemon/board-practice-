package com.mywebsite.www.domain;

import java.util.Arrays;

public class FileUploadDto {
    private int bno;
    private byte[] file;
    private String title;

    public FileUploadDto(){}
    public FileUploadDto(byte[] file, String title){
        this.file = file;
        this.title = title;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FileUploadDto{" +
                "bno=" + bno +
                ", file=" + Arrays.toString(file) +
                ", title='" + title + '\'' +
                '}';
    }
}
