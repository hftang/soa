package com.igeek.ebuy.content.service.impl;

import com.igeek.ebuy.content.service.ContentCategoryService;
import com.igeek.ebuy.manager.mapper.ContentCategoryExMapper;
import com.igeek.ebuy.manager.mapper.TbContentCategoryMapper;
import com.igeek.ebuy.pojo.TbContentCategory;
import com.igeek.ebuy.pojo.TbContentCategoryExample;
import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hftang
 * @date 2019-07-04 14:29
 * @desc
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    private ContentCategoryExMapper contentCategoryExMapper;

    @Override
    public List<EasyUITreeNode> queryCat(long id) {
        TbContentCategoryExample tbexample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbexample.createCriteria();
        criteria.andParentIdEqualTo((long) id);
        criteria.andStatusEqualTo(1);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(tbexample);
        ArrayList<EasyUITreeNode> newList = new ArrayList<>();


        for (TbContentCategory item : list) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();

            easyUITreeNode.setId(item.getId());
            easyUITreeNode.setText(item.getName());
            easyUITreeNode.setState(item.getIsParent() ? "closed" : "open");

            newList.add(easyUITreeNode);
        }
        return newList;
    }

    /**
     * 保存一个类目
     *
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public BuyResult saveCat(int parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();

        tbContentCategory.setParentId((long) parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setIsParent(false);
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);

        contentCategoryExMapper.insert(tbContentCategory);

        //修改父节点为true
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey((long) parentId);
        parent.setIsParent(true);
        tbContentCategoryMapper.updateByPrimaryKey(parent);

        return new BuyResult(200, tbContentCategory);
    }

    //修改叶子节点的名字
    @Override
    public BuyResult updateCat(long id, String name) {

        TbContentCategory cat = tbContentCategoryMapper.selectByPrimaryKey(id);
        cat.setName(name);
        tbContentCategoryMapper.updateByPrimaryKey(cat);

        return new BuyResult(200);
    }

    //删除叶子节点
    @Override
    public BuyResult deleteCat(long id) {


        TbContentCategory cat = tbContentCategoryMapper.selectByPrimaryKey(id);
        cat.setStatus(2);
        tbContentCategoryMapper.updateByPrimaryKey(cat);
        //查下有几个子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(cat.getParentId());
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);

        if (list == null || list.size() == 0) {
            //修改父节点
            TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(cat.getParentId());
            parent.setIsParent(false);
            tbContentCategoryMapper.updateByPrimaryKey(parent);
        }

        return new BuyResult(200);
    }
}
