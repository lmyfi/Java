package com.imooc.mall.common;

import com.imooc.mall.exception.ImoocMallExceptionEnum;

/**
 * 描述： 通用返回对象
 */
public class ApiRestResponse<T> {

    private Integer status;

    private String msg;

    private T data; //数据内容，该数据内容可能为空，也可能包含商品信息或者用户信息

    private static final int OK_CODE = 10000;

    private static final String OK_MSG = "SUCCESS";

    /**
     * 两个参数的构造器
     * @param status
     * @param msg
     */
    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    //无参构造器 请求成功返回信息
    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }

    //没有登录或者其它信息，请求成功就返回正常信息
    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();//调用的就是无参构造方法
    }
    //请求成功 返回包含响应信息的数据对象
    public static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setData(result);//自定义的数据类型
        return response;//放回包含数据内容的，返回对象
    }
    //请求失败，返回错误信息
    public static <T> ApiRestResponse<T> error(Integer code, String msg){
        return new ApiRestResponse<>(code,msg);
    }

    public static <T> ApiRestResponse<T> error(ImoocMallExceptionEnum ex){
        return new ApiRestResponse<>(ex.getCode(), ex.getMsg());
    }

    //为了方便查看打印的信息
    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
