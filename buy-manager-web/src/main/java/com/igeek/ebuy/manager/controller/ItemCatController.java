package com.igeek.ebuy.manager.controller;

import com.igeek.ebuy.service.ItemCatService;
import com.igeek.ebuy.util.pojo.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author hftang
 * @date 2019-06-27 10:26
 * @desc 类别查询
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> queryCat(@RequestParam(defaultValue = "0", name = "id") int parentId) {
        List<EasyUITreeNode> treeNodeList = itemCatService.queryCat(parentId);
        return treeNodeList;
    }


}
