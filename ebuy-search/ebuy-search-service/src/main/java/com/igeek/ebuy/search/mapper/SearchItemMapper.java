package com.igeek.ebuy.search.mapper;

import com.igeek.ebuy.util.pojo.SearchItem;

import java.util.List;

/**
 * @author hftang
 * @date 2019-07-19 10:07
 * @desc
 */
public interface SearchItemMapper {

    public List<SearchItem> getItemList();

    //查询一个商品的信息
    public SearchItem getItemById(long itemId);
}
