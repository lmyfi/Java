package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;

import java.util.List;

/**
 * 商品服务接口类
 */
public interface CategoryService {


    void addCategory(AddCategoryReq addCategoryReq);

    void updateCategory(Category updateCategory);

    void deleteCategory(Integer id);

    PageInfo listCategoryForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryCustom(Integer parentId);
}
