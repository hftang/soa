package com.igeek.ebuy.service;

import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.util.pojo.EasyUIDatagridResult;

/**
 * @author hftang
 * @date 2019-06-24 13:42
 * @desc
 */
public interface ItemService {

    /**
     * 根基商品id 查询商品详情
     *
     * @param itemId
     * @return
     */
    public TbItem queryById(long itemId);

    /**
     * 根据页码和行数查询商品列表
     *
     * @param page
     * @param rows
     * @return
     */
    public EasyUIDatagridResult queryByPage(int page, int rows);
}
