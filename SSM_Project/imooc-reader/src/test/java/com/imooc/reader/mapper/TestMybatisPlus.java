package com.imooc.reader.mapper;

import com.imooc.reader.entity.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)//初始化IOC容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestMybatisPlus {
    @Resource
    private TestMapper testMapper;
    @org.junit.Test
    public void insertTest(){
        Test test = new Test();
        test.setContent("Mybatis-plus测试");
        testMapper.insert(test);
    }
}
