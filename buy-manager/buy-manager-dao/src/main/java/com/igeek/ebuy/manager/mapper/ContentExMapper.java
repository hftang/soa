package com.igeek.ebuy.manager.mapper;

import com.igeek.ebuy.pojo.TbContent;

import java.util.List;
import java.util.Map;

/**
 * @author hftang
 * @date 2019-07-12 15:04
 * @desc
 */
public interface ContentExMapper {

    public List<TbContent> queryByPage(Map map);

    public int queryCount(Map map);
}
