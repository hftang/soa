package com.igeek.ebuy.item.controller;

import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.pojo.TbItemDesc;
import com.igeek.ebuy.service.ItemService;
import com.igeek.ebuy.util.jedis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hftang
 * @date 2019-07-25 13:11
 * @desc
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String itemShow(@PathVariable long itemId, Model model) {

        TbItem tbItem = itemService.queryById(itemId);
        model.addAttribute("item", tbItem);

        //查询商品的详情
        TbItemDesc tbItemDesc = itemService.queryItemDescById(itemId);

        model.addAttribute("itemDesc", tbItemDesc);
        return "item";
    }
}
