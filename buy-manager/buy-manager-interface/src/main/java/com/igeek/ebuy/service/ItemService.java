package com.igeek.ebuy.service;

import com.igeek.ebuy.pojo.TbItem;

/**
 * @author hftang
 * @date 2019-06-24 13:42
 * @desc
 */
public interface ItemService {

    /**
     * 根基商品id 查询商品详情
     * @param itemId
     * @return
     */
    public TbItem queryById(long itemId);
}
