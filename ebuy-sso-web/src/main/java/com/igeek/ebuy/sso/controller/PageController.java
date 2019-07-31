package com.igeek.ebuy.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hftang
 * @date 2019-07-31 14:35
 * @desc
 */

@Controller
public class PageController {

    @RequestMapping("/page/{page}")
    public String showPage(@PathVariable String page) {

        return page;
    }

}
