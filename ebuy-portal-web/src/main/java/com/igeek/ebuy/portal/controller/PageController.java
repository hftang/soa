package com.igeek.ebuy.portal.controller;

import com.igeek.ebuy.content.service.ContentService;
import com.igeek.ebuy.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author hftang
 * @date 2019-07-03 15:33
 * @desc
 */
@Controller
public class PageController {

    @Value("${LUNBAO}")
    private int LUNBAO;

    @Autowired
    private ContentService contentService;


    @RequestMapping("/")
    public String showIndex(Model model) {
        List<TbContent> list = contentService.queryByCategoryId(LUNBAO);
        System.out.println("====>pageController" + list);
        model.addAttribute("ad1List", list);
        return "index";
    }


}
