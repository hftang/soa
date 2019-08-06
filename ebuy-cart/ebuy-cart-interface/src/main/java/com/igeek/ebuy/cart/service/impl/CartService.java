package com.igeek.ebuy.cart.service.impl;

import com.igeek.ebuy.util.pojo.BuyResult;

/**
 * @author hftang
 * @date 2019-08-06 15:05
 * @desc
 */
public interface CartService {


    /**
     * 登录状态下将一个商品加入购物车
     *
     * @param userId 用户id
     * @param itemId 商品id
     * @param num    商品数量
     * @return
     */
    public BuyResult addCart(long userId, long itemId, int num);

    /**
     * 修改购物车中商品
     *
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    public BuyResult updateCart(long userId, long itemId, int num);

    /**
     * 删除购物车
     *
     * @param userId
     * @param itemId
     * @return
     */
    public BuyResult deleteCart(long userId, long itemId);
}