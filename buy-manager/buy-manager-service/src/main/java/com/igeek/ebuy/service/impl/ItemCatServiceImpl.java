package com.igeek.ebuy.service.impl;

import com.igeek.ebuy.manager.mapper.TbItemCatMapper;
import com.igeek.ebuy.pojo.TbItemCat;
import com.igeek.ebuy.pojo.TbItemCatExample;
import com.igeek.ebuy.service.ItemCatService;
import com.igeek.ebuy.util.pojo.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hftang
 * @date 2019-06-27 10:32
 * @desc 根据父id 查询 子类别
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUITreeNode> queryCat(int parentId) {

        TbItemCatExample tbItemExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andParentIdEqualTo((long) parentId);
        List<TbItemCat> itemCatList = tbItemCatMapper.selectByExample(tbItemExample);

        ArrayList<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();

        for (TbItemCat item : itemCatList) {

            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(item.getId());
            easyUITreeNode.setText(item.getName());
            easyUITreeNode.setState(item.getIsParent() ? "closed" : "open");

            easyUITreeNodes.add(easyUITreeNode);
        }

        return easyUITreeNodes;
    }
}
