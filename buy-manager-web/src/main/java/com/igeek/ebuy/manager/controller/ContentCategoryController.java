package com.igeek.ebuy.manager.controller;

import com.igeek.ebuy.content.service.ContentCategoryService;
import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author hftang
 * @date 2019-07-04 14:22
 * @desc
 */

@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCatService;

    //删除叶子节点
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public BuyResult delete(long id) {

        return contentCatService.deleteCat(id);
    }

    //修改叶子节点的名字
    @RequestMapping("/content/category/update")
    @ResponseBody
    public BuyResult updateCat(long id, String name) {

        return contentCatService.updateCat(id, name);
    }

    //保存分类
    @RequestMapping("/content/category/create")
    @ResponseBody
    public BuyResult createCat(int parentId, String name) {

        return contentCatService.saveCat(parentId, name);
    }


    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> queryCat(@RequestParam(defaultValue = "0") long id) {
        List<EasyUITreeNode> easyUITreeNodes = contentCatService.queryCat(id);
        return easyUITreeNodes;
    }
}
