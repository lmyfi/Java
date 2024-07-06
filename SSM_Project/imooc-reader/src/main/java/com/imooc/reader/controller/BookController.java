package com.imooc.reader.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.*;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.CategoryService;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

//控制类，起到了承上启下的作用，实现具体方法时一定要注意参数名必须与实体类中的属性名相同，不然可能会出错
@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;//图书分页对象
    @Resource
    private EvaluationService evaluationService;//评论
    @Resource
    private MemberService memberService;//会员服务

    @GetMapping("/index")
    public ModelAndView showIndex(){
        ModelAndView mav = new ModelAndView("/index");
        List<Category> list = categoryService.selectAll();
        mav.addObject("categoryList",list);
        return mav;
    }


    /**
     * 服务端提供给前端的数据支持
     * @param categoryId 图书类别
     * @param order 排序方式
     * @param p 查询页号
     * @return
     */
    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Long categoryId, String order,Integer p){
        if (p==null){
            p = 1;
        }
        IPage<Book> pageObject = bookService.paging(categoryId, order, p, 10);
        return pageObject;
    }

    @GetMapping("/book/{id}")//这里的id与下面的参数id为映射
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session){//设置路径变量，可以根据点击时不同，产生新的url，访问/查询到新的资源
        Book book = bookService.selectById(id);
        List<Evaluation> evaluationList = evaluationService.selectByBookId(id);
        Member member = (Member) session.getAttribute("loginMember");//拿到登录用户的信息
        ModelAndView mav = new ModelAndView("/detail");
        if (member != null){
            //获取会员阅读状态
            MemberReadState memberReadState = memberService.selectMemberReadState(member.getMemberId(), id);
            mav.addObject("memberReadState",memberReadState);
            mav.addObject("loginMember",member);
        }
        mav.addObject("book",book);
        mav.addObject("evaluationList",evaluationList);
        return mav;
    }

}
