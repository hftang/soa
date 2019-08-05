package com.igeek.ebuy.sso.controller;

import com.igeek.ebuy.pojo.TbUser;
import com.igeek.ebuy.sso.service.UserService;
import com.igeek.ebuy.util.cookie.CookieUtils;
import com.igeek.ebuy.util.pojo.BuyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hftang
 * @date 2019-07-31 16:02
 * @desc
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user/check/{data}/{type}")
    @ResponseBody
    public BuyResult checkData(@PathVariable String data, @PathVariable int type) {
        BuyResult result = userService.checkData(data, type);
        return result;
    }

    //注册
    @RequestMapping("/user/register")
    @ResponseBody
    public BuyResult register(TbUser tbUser) {
        BuyResult result = userService.register(tbUser);
        return result;
    }

    //登录
    @RequestMapping("/user/login")
    @ResponseBody
    public BuyResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        BuyResult result = userService.login(username, password);
        String token = result.getMsg();
        if (StringUtils.isNotBlank(token)) {
//            Cookie cookie = new Cookie("EBUY-TOKEN", token);
//            //修改域 不设置是默认当前访问域名
//            cookie.setDomain(".ebuy.com");
//            cookie.setPath("/");
//            response.addCookie(cookie);

            CookieUtils.setCookie(request,response,"EBUY-TOKEN",token);
        }

        return result;
    }

}
