package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Category;
import com.imooc.reader.mapper.CategoryMapper;
import com.imooc.reader.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("CategoryService") //这里beanId与接口名字相同，更符合规则
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true) //开启事务，配置为不使用事务，传播特性为只读
public class CategoryServiceImpl implements CategoryService {
    @Resource //拿到对应的Mapper类
    private CategoryMapper categoryMapper;
    /**
    *查询所用图书分类
    * @return 图书分类List
    * */
    @Override
    public List<Category> selectAll() {
        List<Category> list = categoryMapper.selectList(new QueryWrapper<>());
        return list;
    }
}
