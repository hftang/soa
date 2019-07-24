package com.igeek.ebuy.search.service;

import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.SearchResult;

/**
 * @author hftang
 * @date 2019-07-19 9:49
 * @desc
 */
public interface SearchService {

    /**
     * 一键导入索引库
     */
    public BuyResult importIndex();

    /**
     * 查询商品
     *
     * @param page
     * @param rows
     * @param keyword
     * @return
     */
    public SearchResult searchItem(int page, int rows, String keyword);
}
