package com.igeek.ebuy.service;

import com.igeek.ebuy.util.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author hftang
 * @date 2019-06-27 10:30
 * @desc 分类
 */
public interface ItemCatService {

    /***
     * 根据父id 查询子目录
     * @param parentId
     * @return
     */
    public List<EasyUITreeNode> queryCat(int parentId);
}
