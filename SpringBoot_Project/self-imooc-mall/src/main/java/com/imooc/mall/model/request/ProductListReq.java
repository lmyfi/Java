package com.imooc.mall.model.request;

import java.util.Date;

/**
 * 商品列表(用户)接口，参数封装类
 */
public class ProductListReq {

    private Integer keyword;//主键id

    private Integer categoryId;//目录id

    private String orderBy;//排序方式

    private Integer pageNum = 1;//分页后第几页的页号，默认1条
    private Integer pageSize = 10;//一页包含几条信息，默认10条

    @Override
    public String toString() {
        return "ProductListReq{" +
                "keyword=" + keyword +
                ", categoryId=" + categoryId +
                ", orderBy='" + orderBy + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }

    public Integer getKeyword() {
        return keyword;
    }

    public void setKeyword(Integer keyword) {
        this.keyword = keyword;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}