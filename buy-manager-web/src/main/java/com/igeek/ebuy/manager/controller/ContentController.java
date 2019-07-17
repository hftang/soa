package com.igeek.ebuy.manager.controller;

import com.igeek.ebuy.content.service.ContentService;
import com.igeek.ebuy.pojo.TbContent;
import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.EasyUIDatagridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hftang
 * @date 2019-07-12 10:19
 * @desc
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;


    /**
     * 分页查询
     *
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDatagridResult queryContent(long categoryId, int page, int rows) {

        return contentService.queryContent(categoryId, page, rows);

    }


    @RequestMapping("/content/save")
    @ResponseBody
    public BuyResult savecontent(TbContent tbContent) {

        BuyResult buyResult = contentService.saveContent(tbContent);

        return buyResult;
    }
}
