package com.igeek.ebuy.manager.controller;

import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.service.ItemService;
import com.igeek.ebuy.util.pojo.EasyUIDatagridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author hftang
 * @date 2019-06-24 13:38
 * @desc
 */

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem queryById(@PathVariable long itemId) {

        //因为配置了 responseBody 所以 直接返回json对象 不走视图解析器
        return itemService.queryById(itemId);
    }

    //分页查询商品列表
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDatagridResult itemList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int rows) {
        EasyUIDatagridResult easyUIDatagridResult = itemService.queryByPage(page, rows);
        return easyUIDatagridResult;
    }
}
