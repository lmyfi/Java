package com.imooc.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *  使用 AOP 打印请求和响应信息
 */
@Aspect
@Component
public class WebLogAspect {
    private final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    //切点     为controller类中的所有方法
    //拦截点
    @Pointcut("execution(public * com.imooc.mall.controller.*.*(..))")
    public void webLog(){

    }

    @Before("webLog()") //处理请求前
    public void doBefore(JoinPoint joinPoint){
        //收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("URL : " + request.getRequestURL().toString());//请求URL
        log.info("HTTP_METHOD : " + request.getMethod());//请求方法
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName()
         + "." + joinPoint.getSignature().getName());//类型和名字
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));//获取参数
    }

    @AfterReturning(returning = "res", pointcut = "webLog()") //返回结果后
    public void doAfterReturning(Object res) throws JsonProcessingException {
        //处理完请求，返回内容
        //ObjectMapper().writeValueAsString(res)将对象res转成Json类型
        log.info("RESPONSE : " + new ObjectMapper().writeValueAsString(res));
    }
}
