package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台商品管理 controller
 */
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("商品详情接口")
    @PostMapping("product/detail")
    public ApiRestResponse detail(@RequestParam Integer id){
        Product productDetail = productService.detail(id);
        return ApiRestResponse.success(productDetail);
    }

    @ApiOperation("商品列表接口")
    @PostMapping("product/list")
    public ApiRestResponse list(ProductListReq productListReq){
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);
    }
}
