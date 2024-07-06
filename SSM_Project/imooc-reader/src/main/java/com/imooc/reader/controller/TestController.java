package com.imooc.reader.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {
    //测试freemarker模板引擎是否配置成功
    @GetMapping("/test/t1")
    public ModelAndView test1(){
        return new ModelAndView("/test");
    }
    //测试JSON序列化输出配置是否成功
    @GetMapping("/test/t2")
    @ResponseBody
    public Map test2(){
        Map result = new HashMap();
        result.put("test","测试文本");
        return result;
    }
}
