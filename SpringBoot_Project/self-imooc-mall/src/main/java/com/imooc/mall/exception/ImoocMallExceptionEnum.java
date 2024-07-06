package com.imooc.mall.exception;

/**
 * 描述：  异常枚举类
 */
public enum ImoocMallExceptionEnum {
    NEED_USER_NAME(10001,"用户名不能为空"),
    NEED_PASSWORD(10002,"密码不能为空"),
    PASSWORD_TOO_SHORT(10003,"密码不能少于8位"),
    NAME_EXISTED(10004,"不允许重名"),
    INSERT_FAILED(10005,"用户信息插入失败，请重试"),
    WRONG_PASSWORD(10006,"登录失败，用户名或密码错误"),
    NEED_LOGIN(10007,"用户未登录"),
    UPDATE_FAILED(10008,"更新失败"),
    NEED_ADMIN(10009,"需要管理员身份"),
    PARA_NOT_NULL(10010,"参数不能为空"),
    CREATE_FAILED(10011,"创建失败"),
    REQUEST_PARA_ERROR(10012,"请求参数错误"),
    DELETE_FAILED(10013,"删除失败"),
    MKDIR_FAILED(10014,"创建目录失败"),
    UPLOAD_FAILED(10015,"上传失败"),
    NOT_SELL(10016,"商品已下架"),
    NOT_ENOUGH(10017,"商品不足"),
    CART_EMPTY(10018, "购物车已勾选的商品为空"),
    NO_ENUM(10019, "未找到对应的枚举"),
    NO_ORDER(10020, "订单不存在"),
    NOT_YOUR_ORDER(10021, "订单不属于你"),
    WRONG_ORDER_STATUS(10022, "订单状态不符"),
    READ_ONLY(10023, "为保证数据正确，本系统仅用于演示后台显示功能，已将修改相关功能关闭"),
    SYSTEM_ERROR(20001,"系统异常");

    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
