package com.imooc.mall.exception;

import com.imooc.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 藐视：         处理统一异常的handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    //记录异常
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e){
        log.error("Default Exception: ",e);
        //将系统异常统一化为ApiRestResponse返回对象
        return ApiRestResponse.error(ImoocMallExceptionEnum.SYSTEM_ERROR);
    }
    @ExceptionHandler(ImoocMallException.class)
    @ResponseBody
    public Object handleException(ImoocMallException e){
        log.error("Default Exception: ",e);
        //ApiRestResponse返回对象,使用异常ImoocMallException对象中的code和message，构造统一的返回对象
        return ApiRestResponse.error(e.getCode(),e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handlerMethodArgumentNotValidaException(MethodArgumentNotValidException e){
        log.error("handlerMethodArgumentNotValidaException:", e);
        return handlerMethodArgumentNotValidaException(e.getBindingResult());
    }

    private ApiRestResponse handlerMethodArgumentNotValidaException(BindingResult result) {
        //把异常处理为对外暴露的提示
        List list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0){
            return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARA_ERROR);
        }
        //将由状态码和系统异常信息构造的统一返回对象的error方法返回去
        return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARA_ERROR.getCode(),list.toString());
    }
}
