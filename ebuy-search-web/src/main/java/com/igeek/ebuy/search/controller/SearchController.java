package com.igeek.ebuy.search.controller;

import com.igeek.ebuy.search.service.SearchService;
import com.igeek.ebuy.util.pojo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hftang
 * @date 2019-07-22 11:18
 * @desc
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "60") int rows, Model model) {

        try {


            keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
            SearchResult searchResult = searchService.searchItem(page, rows, keyword);
            //搜索关键字
            model.addAttribute("query", keyword);
            model.addAttribute("page", page);
            model.addAttribute("totalPages", searchResult.getTotalPages());
            model.addAttribute("recordCount", searchResult.getRecourdCount());
            model.addAttribute("itemList", searchResult.getItemList());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "search";

    }
}
