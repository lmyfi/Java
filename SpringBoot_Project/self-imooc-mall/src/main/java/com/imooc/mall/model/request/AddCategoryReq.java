package com.imooc.mall.model.request;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 商品分类  请求参数封装类
 */
public class AddCategoryReq {

    @Size(min = 2,max = 5)
    @NotNull
    private String name; //目录名

    @Max(3)
    @NotNull
    private Integer type; //目录层级

    @NotNull(message = "parentId不能为null")
    private Integer parentId;//父目录ID

    @NotNull(message = "orderNum不能为空")
    private Integer orderNum;//排序

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "AddCategoryReq{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
    }
}
