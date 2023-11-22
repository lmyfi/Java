package com.imooc.reader.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/management")//统一请求前缀
public class ManagementController {
    @GetMapping("/index.html")
    public ModelAndView showPage(){
        return new ModelAndView("/management/index");
    }
}
