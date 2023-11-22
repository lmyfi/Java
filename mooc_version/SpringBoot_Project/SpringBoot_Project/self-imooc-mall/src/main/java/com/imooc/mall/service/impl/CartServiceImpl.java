package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Cart;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车模块服务实现类
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;

    /**
     * 商品列表查询接口
     * @param userId
     * @return
     */
    @Override
    public List<CartVO> list(Integer userId) {
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            //总和：单价*数量
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }
    /**
     * 添加商品到购物车服务
     * @param userId 购物车对应--用户id
     * @param productId 商品id
     * @param count 购买订单数量
     * @return
     */
    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count){
        //验证商品可用性
        validProduct(productId,count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null){
            //这个商品之前不在购物车里，需要新增一个记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.SELECTED);
            cartMapper.insertSelective(cart);
        }else {
            //这个商品已经在购物撤离了，则数量相加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setProductId(productId);
            cartNew.setUserId(userId);
            cartNew.setQuantity(count);
            cartNew.setSelected(Constant.Cart.SELECTED);//默认选择勾选
            cartNew.setId(cart.getId());
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    /**
     * 校验要添加到购物车的商品是否还可以添加
     * @param productId 商品id
     * @param count 用户预计商品添加数量
     */
    private void validProduct(Integer productId, Integer count){
        //获取添加商品的id的商品信息
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null || product.getStatus().equals(Constant.SellStatus.NOT_SALE)){
            //商品不存在和商品下架，抛出异常
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SELL);
        }
        //校验购买数量是否大于库存
        if (count > product.getStock()){
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }
    }

    /**
     * 更新购物车
     * @param userId
     * @param productId
     * @param count
     */
    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count){
        //验证商品可用性
        validProduct(productId,count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null){
            //没有拿到购物车，无法进行更新操作，抛出异常
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }else {
            //更新商品
            Cart cartNew = new Cart();
            cartNew.setProductId(productId);
            cartNew.setUserId(userId);
            cartNew.setQuantity(count);
            cartNew.setSelected(Constant.Cart.SELECTED);//默认选择勾选
            cartNew.setId(cart.getId());
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法删除
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        } else {
            //这个商品已经在购物车里了，则可以删除
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法选择/不选中
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        } else {
            //这个商品已经在购物车里了，则可以选中/不选中
            cartMapper.selectOrNot(userId, productId, selected);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> selectAllOrNot(Integer userId, Integer selected) {
        //改变选中状态
        cartMapper.selectOrNot(userId, null, selected);
        return this.list(userId);
    }
}
