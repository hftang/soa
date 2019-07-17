package com.igeek.ebuy.content.service;

import com.igeek.ebuy.pojo.TbContent;
import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.EasyUIDatagridResult;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * @author hftang
 * @date 2019-07-12 10:49
 * @desc
 */
public interface ContentService {

    public BuyResult saveContent(TbContent tbContent);

    //查询所有
    public EasyUIDatagridResult queryContent(long categoryId, int page, int rows);
    //
    public List<TbContent> queryByCategoryId(long categoryId);
}
