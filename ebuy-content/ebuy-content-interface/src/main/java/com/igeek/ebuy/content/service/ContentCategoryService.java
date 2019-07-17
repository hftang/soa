package com.igeek.ebuy.content.service;

import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author hftang
 * @date 2019-07-04 14:28
 * @desc
 */
public interface ContentCategoryService {

    public List<EasyUITreeNode> queryCat(long id);

    //插入一个条目
    public BuyResult saveCat(int parentId, String name);

    //修改子叶子节点
    BuyResult updateCat(long id, String name);

    //删除叶子节点
    BuyResult deleteCat(long id);

}
