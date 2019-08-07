package com.igeek.ebuy.cart.service.impl;

import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.util.pojo.BuyResult;

import java.util.List;

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

    /***
     *合并购物车
     * @param usrId 用户id
     * @param cartList cookie中的购物车列表
     * @return
     */

    public BuyResult mergeCart(long usrId, List<TbItem> cartList);

    /**
     * 获取购物车列表
     *
     * @param userId
     * @return
     */
    public List<TbItem> getCartList(long userId);
}