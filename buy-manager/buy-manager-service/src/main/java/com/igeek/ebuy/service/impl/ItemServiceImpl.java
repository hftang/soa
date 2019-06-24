package com.igeek.ebuy.service.impl;

import com.igeek.ebuy.manager.mapper.TbItemMapper;
import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.pojo.TbItemExample;
import com.igeek.ebuy.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hftang
 * @date 2019-06-24 13:46
 * @desc
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TbItem queryById(long itemId) {

//        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);

        //第二种 使用 example

        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andIdEqualTo(itemId);


        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);

        if (tbItems != null && tbItems.size() > 0) {
            return tbItems.get(0);
        }
        return null;
    }
}
