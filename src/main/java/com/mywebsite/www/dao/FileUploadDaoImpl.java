package com.mywebsite.www.dao;

import com.mywebsite.www.domain.FileUploadDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {
    @Autowired
    SqlSession session;
    private static final String namespace = "com.mywebsite.www.dao.FileUploadDao.";


    @Override
    public int insert(FileUploadDto dto) throws Exception {
        return session.insert(namespace+"insert",dto);
    }

    @Override
    public List<FileUploadDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    }

}
