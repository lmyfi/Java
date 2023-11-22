package com.imooc.mall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CategoryMapper;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.UpdateCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品分类服务接口CategoryService 服务实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void addCategory(AddCategoryReq addCategoryReq){
        //商品分类实体
        Category category = new Category();
        /**
         * BeanUtils.copyProperties(参数对象，要赋值到的对象) 参数赋值工具类，可以简化我们对目标对象实体的繁琐赋值，
         * 比如使用Setter方法一个一个赋值。
         *它们会按照统一字段名进行赋值
         * addCategoryReq 要添加的分类参数
         * category 新创建的实体对象
         */
        BeanUtils.copyProperties(addCategoryReq,category);
        //查询分类表中是否存在要添加的，分类名
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //更新条数
        int count = categoryMapper.insertSelective(category);
        if (count == 0){
            //更新条数为0，即创建商品分类失败
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }
    }

    /**
     * 更新参数
     * @param updateCategory 更新后的目录对象
     */
    @Override
    public void updateCategory(Category updateCategory){
        if (updateCategory.getName() != null){
            //通过现有category对象的名字属性值，去获取旧的目录对象
            Category categoryOld = categoryMapper.selectByName(updateCategory.getName());
            if (categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())){
                //获取到要更新的categoryOld对象不为空，并且旧的category对象与新的对象的id属性值不相同（名字相同，id不同，出现重名）
                //抛出重名异常
                throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
            }
        }
        //进行更新操作
        int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if (count == 0){
            //更新条数为0 更新失败，抛出异常
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 删除目录
     * @param id
     */
    @Override
    public void deleteCategory(Integer id){
        //查询是否存在该id目录
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        if (categoryOld == null){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        //存在该id对应的分类目录信息
        //执行删除操作
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0){
            //更新条数为零，删除失败抛出异常
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
    }

    /**
     * 分页查询
     * @param pageNum 页数
     * @param pageSize 一页包含几条
     * @return
     */
    @Override
    public PageInfo listCategoryForAdmin(Integer pageNum, Integer pageSize){
        //orderBy : 优先以type字段排序，后以order_num字段排序
        //设置分页属性
        PageHelper.startPage(pageNum, pageSize, "type,order_num");
        //进行数据查询,将分类目录信息全部查找出来
        List<Category> categoryList = categoryMapper.selectList();
        //分页查询接口
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    /**
     * 给普通用户查看的目录
     * @return
     */
    @Override
    @Cacheable(value = "listCategoryCustom")
    public List<CategoryVO> listCategoryCustom(Integer parentId){
        ArrayList<CategoryVO> categorieVOList = new ArrayList<>();
        recursivelyFindCategories(categorieVOList,parentId);
        return categorieVOList;
    }

    /**
     * 递归查询父类信息，并构建新的数据结构CategoryVO对象，并存放在categoryVOList中
     * @param categoryVOList 给前端看的数据层次结构 ”目录树“
     * @param parentId
     */
    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId){
        //递归获取所有子类别,并组合成为一个”目录树“
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);

        //递归结束条件为  子目录为空
        if (!CollectionUtils.isEmpty(categoryList)){
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                //将category对象参数赋值给categoryVO对象中与category对象对应字段赋值
                BeanUtils.copyProperties(category,categoryVO);
                categoryVOList.add(categoryVO);//将构造好的categoryVO对象，存放的存储categoryVO的列表中去

                //递归
                recursivelyFindCategories(categoryVO.getChildCategory(),category.getId());
            }
        }
    }
}
