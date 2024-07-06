package com.imooc.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    private Producer kaptchaProducer;

    @GetMapping("/verify_code")   //kaptcha验证码组件，没有默认使用springMVC，所以需要调用底层Servlet实现
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //响应立即过期
        response.setDateHeader("Expires",0);//每次刷新，都要生成一个新的验证码图片
        //不缓存任何图片数据，保证验证的可靠性
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/png");
        //生成验证码图片
        String verifyCode = kaptchaProducer.createText();
        request.getSession().setAttribute("kaptchaVerifyCode",verifyCode);
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));
        BufferedImage image = kaptchaProducer.createImage(verifyCode);//创建验证码图片
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"png",out);//将服务器端的图片在用户端浏览器上显示出来
        out.flush();//立即刷新
        out.close();//关闭流
    }
}
