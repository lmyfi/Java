package com.imooc.reader.task;

import com.imooc.reader.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 完成自动计算任务
 */
@Component
public class ComputerTask {
    @Resource
    private BookService bookService;
    //任务调度
    @Scheduled(cron = "0 * * * * ?") //Cron表达式，该表示代表按1分种时间间隔执行
    public void updateEvaluation(){
        bookService.updateEvaluation();
        System.out.println("已更新多有图书评分");
    }
}
