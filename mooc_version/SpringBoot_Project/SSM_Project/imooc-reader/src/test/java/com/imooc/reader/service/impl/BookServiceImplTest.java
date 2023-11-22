package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BookServiceImplTest {
    @Resource
    private BookService bookService;
    @Test
    public void paging() {
        IPage<Book> page = bookService.paging(3l,"score",1, 10);
        List<Book> bookList = page.getRecords();
        for (Book b : bookList){
            System.out.println(b.getBookId() + ":" + b.getBookName());
        }
        System.out.println("总页数："+ page.getPages());
        System.out.println("总记录数："+ page.getTotal());
    }
}