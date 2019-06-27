package com.igeek.ebuy.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hftang
 * @date 2019-06-26 13:22
 * @desc
 */

@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex(){

        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
