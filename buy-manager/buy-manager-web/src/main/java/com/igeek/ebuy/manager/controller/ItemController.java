package com.igeek.ebuy.manager.controller;

import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
