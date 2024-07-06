package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.exception.BussinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
public class MBookController {
    @Resource
    private BookService bookService;
    @GetMapping("/index.html")
    public ModelAndView showBook(){
        return new ModelAndView("/management/book");
    }

    /**
     * wangEditor 文件上传
     * @param file  上传文件
     * @param request 原生请求对象
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody //该注解为返回类型为Json数据类型
    public Map upload(@RequestParam("img")MultipartFile file, HttpServletRequest request) throws IOException {
        //得到上传目录 一般获取到的目录为该工程下的out/artifacts/..目录，也可能为target目录
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        //文件名  以时间精确到毫秒为文件命名方式，确保每张图片名字不同
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //扩展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存文件到upload目录
        file.transferTo(new File(uploadPath + fileName + suffix));
        Map result = new HashMap();
        result.put("errno", 0);
        //到这，可以直接通过浏览器到，所有不用uploadPath
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book){
        Map result = new HashMap();
        try{
            book.setEvaluationQuantity(0);//评价人数
            book.setEvaluationScore(0f);//评价分数
            Document doc = Jsoup.parse(book.getDescription());//解析图书详情
            Element img = doc.select("img").first();//获取图书详情第一张图的元素对象
            String cover = img.attr("src");//第一张图的url资源路径值
            book.setCover(cover);
            bookService.createBook(book);
            result.put("code",0);
            result.put("msg","success");
        }catch (BussinessException ex){
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMessage());
        }
        return result;
    }

    /**
     * 图书管理页，显示图书列表，返回Json数据，用于填充layui页面
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list")
    @ResponseBody //返回Json序列化数据
    public Map list(Integer page, Integer limit){
        if (page == null){
            page = 1;//当page为空时，默认设置为1，显示一页
        }
        if (limit == null){
            limit = 10; //为空时，默认显示10条数据
        }

        IPage<Book> pagObject = bookService.paging(null, null, page, limit);//categoryId:null 不用分类， order:null不用排序,显示
        Map result = new HashMap();
        result.put("code","0");
        result.put("msg","success");
        result.put("data", pagObject.getRecords());//当前页面数据
        result.put("count",pagObject.getTotal());//未分页时记录总数
        return result;
    }

    /**
     * 回填图书信息，发送ajax请求,获取对应图书信息
     * @param bookId
     * @return
     */
    @GetMapping("/id/{id}")
    @ResponseBody
    public Map selectById(@PathVariable("id") Long bookId){
        Book book = bookService.selectById(bookId);
        HashMap result = new HashMap();
        result.put("code","0");
        result.put("msg","success");
        result.put("data",book);
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book){
        HashMap result = new HashMap();
        try{
            Book rawBook = bookService.selectById(book.getBookId());//获取原始图书数据
            rawBook.setBookName(book.getBookName());
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setDescription(book.getDescription());
            Document doc = Jsoup.parse(book.getDescription());//解析HTML
            String cover = doc.select("img").first().attr("src");//获取src属性值
            rawBook.setCover(cover);
            bookService.updateBook(book);//更新数据
            result.put("code","0");
            result.put("msg","success");
        }catch (BussinessException ex){
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId){
        HashMap result = new HashMap();
        try{
            bookService.deleteBook(bookId);//删除操作
            result.put("code","0");
            result.put("msg","success");
        }catch (BussinessException ex){
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }
}
