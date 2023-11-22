package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberReadStateMapper;
import com.imooc.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 实现分页查询业务
 */
@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;//bookmapper继承了MyBatis-Plus的核心实现
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;
    /**
     * 分页查询图书
     * @param categoryId 分类编号
     * @param order 排序方式
     * @param page 页号
     * @param rows  每页记录数
     * @return 分页对象
     * @return
     */
    @Override
    public IPage<Book> paging(Long categoryId,String order,Integer page, Integer rows) {
        Page<Book> p = new Page<>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>(); //封装查询
        //重构查询
        if (categoryId != null && categoryId != -1){
            queryWrapper.eq("category_id",categoryId);
        }
        if (order != null){
            //数量
            if (order.equals("quantity")){
                queryWrapper.orderByDesc("evaluation_quantity");
            }else if (order.equals("score")){
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }

    /**
     * 根据图书编号查询图书对象
     * @param bookId 图书编号
     * @return 图书对象
     */
    public Book selectById(Long bookId){
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    /**
     * 更新图书评分/评价数量
     */
    @Override
    @Transactional  //要执行事务操作
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    /**
     * 创建添加新的图书
     * @param book 图书对象
     * @return
     */
    @Override
    @Transactional //需要对数据库进行写操作，需要需要开启事务管理
    public Book createBook(Book book) {
        bookMapper.insert(book);//使用MyBatis-plus提供的接口插入新的图书
        return book;
    }

    /**
     * 更新图书数据信息
     * @param book 新图书数据
     * @return
     */
    @Transactional
    @Override
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Transactional //开启事务
    @Override
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);//删除图书编号为bookId的图书信息表中的数据
        //阅读状态表数据删除操作
        QueryWrapper<MemberReadState> memberReadStateQueryWrapper = new QueryWrapper<>();
        memberReadStateQueryWrapper.eq("book_id", bookId);
        memberReadStateMapper.delete(memberReadStateQueryWrapper);//删除对应的图书编号阅读状态的数据库表
        //用户评论表数据删除操作
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<>();
        evaluationQueryWrapper.eq("book_id",bookId);
        evaluationMapper.delete(evaluationQueryWrapper);
    }
}
