package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.query.ProductListQuery;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper; //获取mybatis映射类
    @Autowired
    CategoryService categoryService;

    /**
     * 添加商品服务
     * @param addProductReq
     */
    @Override
    public void addProduct(AddProductReq addProductReq){
        //要添加商品，所以先要创建一个商品对象
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq,product);//参数赋值
        //查看数据库中是否有与要添加的商品名字相同的商品
        Product productOld = productMapper.selectByName(addProductReq.getName());
        if (productOld != null){
            //添加商品与数据库中的已有商品重名，抛出异常
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //没有重名，执行插入操作
        int count = productMapper.insertSelective(product);
        if (count == 0){
            //更新条数为0，添加商品失败
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }
    }

    /**
     * 更新商品信息接口服务实现
     * @param product
     */
    @Override
    public void updateProduct(Product product){
        //查询是否存在重名但id不同
        Product productOld = productMapper.selectByName(product.getName());
        //校验
        if (productOld != null && !productOld.getId().equals(product.getId())){
            //要更新的对象，同名，不同id，抛出重名异常
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //检验通过，更新数据
        int count = productMapper.updateByPrimaryKeySelective(product);
        if (count == 0){
            //更新条数为0.抛出更新失败异常
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 删除商品接口实现
     * @param id 商品id
     */
    @Override
    public void deleteProduct(Integer id){
        //查询是否存在重名但id不同
        Product productOld = productMapper.selectByPrimaryKey(id);
        //校验
        if (productOld == null){
            //不存在，抛出异常
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        //检验通过，更新数据
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0){
            //更新条数为0.抛出更新失败异常
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellstatus){
        //更新ids数组内包含的id的上下架信息
        int count = productMapper.batchUpdateSellStatus(ids, sellstatus);
    }

    /**
     * 分页查询商品列表信息
     * @param pageNum 第几页
     * @param pageSize  一页包含几条商品信息
     */
    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);//设置分页参数
        //查询出所有商品信息
        List<Product> productsList = productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(productsList);
        return pageInfo;
    }

    /**
     * 商品详情查询
     * @param id 商品id
     * @return
     */
    @Override
    public Product detail(Integer id){
        //通过主键id查询商品
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    @Override
    public PageInfo list(ProductListReq productListReq){
        //构建Query对象
        ProductListQuery productListQuery = new ProductListQuery();

        //搜素处理
        if (!StringUtils.isEmpty(productListQuery.getKeyword())){
            //如果查询到用户进行筛选，因为关键字keyword为不为空，进行搜素关键字构建
            String keyword = new StringBuilder().append("%").append(productListReq.getKeyword())
                    .append("%").toString();
            productListQuery.setKeyword(keyword);
        }

        //目录处理，如果查某个目录下的商品。不仅是需要查出该目录下的，还要
        // 把所有子目录的所有商品都查出来，所以要拿到一个目录id的List
        if (productListReq.getCategoryId() != null){
            //拿到同catagoryId目录
            List<CategoryVO> categoryVOList = categoryService.listCategoryCustom(productListReq.getCategoryId());
            //新建列表，用于存放
            ArrayList<Integer> categoryIds = new ArrayList<>();
            //存放商品列表目录id
            categoryIds.add(productListReq.getCategoryId());
            //填充categoryIds列表，子目录
            getCategoryIds(categoryVOList,categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }
        //排序处理

        //获取用户是否需要进行排序
        String orderBy = productListReq.getOrderBy();
        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
            //需要排序，进行按指定规则排序，价格升序/降序
            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize(),orderBy);
        }else {
            //不需要排序，就不用再分页时添加排序规则
            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize());
        }
        //使用构建好的复杂查询，进行按目录--》子目录查询
        List<Product> productList = productMapper.selectList(productListQuery);
        //将查询结果传入，进行分页
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }

    private void getCategoryIds(List<CategoryVO> categoryVOList, ArrayList<Integer> categoryIds){
        for (int i = 0; i < categoryVOList.size(); i++) {
            CategoryVO categoryVO = categoryVOList.get(i);
            if (categoryVO != null){
                categoryIds.add(categoryVO.getId());
                getCategoryIds(categoryVO.getChildCategory(),categoryIds);
            }
        }
    }

}
