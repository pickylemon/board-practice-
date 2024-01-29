package com.mywebsite.www.dao;

import com.mywebsite.www.domain.FileUploadDto;

import java.util.List;

public interface FileUploadDao {
    int insert(FileUploadDto dto) throws Exception;
    List<FileUploadDto> selectAll() throws Exception;
}
