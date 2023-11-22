package com.imooc.mall.model.dao;

import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.vo.CategoryVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Category selectByName(String name);

    //查询全部数据
    List<Category> selectList();

    List<Category> selectCategoriesByParentId(Integer parentId);
}