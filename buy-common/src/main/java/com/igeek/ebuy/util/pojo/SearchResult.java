package com.igeek.ebuy.util.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author hftang
 * @date 2019-07-22 13:49
 * @desc
 */
public class SearchResult implements Serializable {
    //查询列表
    private List<SearchItem> itemList;
    //总页数
    private int totalPages;
    //记录数
    private int recourdCount;

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRecourdCount() {
        return recourdCount;
    }

    public void setRecourdCount(int recourdCount) {
        this.recourdCount = recourdCount;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "itemList=" + itemList +
                ", totalPages=" + totalPages +
                ", recourdCount=" + recourdCount +
                '}';
    }
}
