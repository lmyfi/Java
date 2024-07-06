package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.ProductListReq;

public interface ProductService {
    void addProduct(AddProductReq addProductReq);

    void updateProduct(Product product);

    void deleteProduct(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellstatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);
}
