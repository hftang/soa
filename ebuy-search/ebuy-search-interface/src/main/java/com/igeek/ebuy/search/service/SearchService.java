package com.igeek.ebuy.search.service;

import com.igeek.ebuy.util.pojo.BuyResult;

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
}
