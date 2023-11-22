package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.request.CreateOrderReq;

import com.imooc.mall.model.vo.OrderVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述：     订单Service
 */
public interface OrderService {

    //数据库事务
    @Transactional(rollbackFor = Exception.class)
    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    String qrcode(String orderNo);

    void pay(String orderNo);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    //发货
    void deliver(String orderNo);

    void finish(String orderNo);
}
