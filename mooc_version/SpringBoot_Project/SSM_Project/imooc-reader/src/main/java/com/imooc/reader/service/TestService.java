package com.imooc.reader.service;

import com.imooc.reader.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private TestMapper testMapper;

    public void BatchImport(){
        for (int i=0; i<5; i++){
            testMapper.insertSample();
        }
    }

}
