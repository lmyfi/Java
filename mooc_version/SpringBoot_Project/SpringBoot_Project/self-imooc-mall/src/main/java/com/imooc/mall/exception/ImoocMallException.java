package com.imooc.mall.exception;

/**
 * 描述    统一异常类
 */
public class ImoocMallException extends RuntimeException{
    private final Integer code; //状态码
    private final String message;//异常信息

    public ImoocMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ImoocMallException(ImoocMallExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(),exceptionEnum.getMsg());//调用上面定义的构造器，使用异常枚举类构造
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
