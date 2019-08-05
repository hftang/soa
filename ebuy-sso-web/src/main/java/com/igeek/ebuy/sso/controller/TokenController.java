package com.igeek.ebuy.sso.controller;

import com.igeek.ebuy.sso.service.TokenService;
import com.igeek.ebuy.util.json.JsonUtils;
import com.igeek.ebuy.util.pojo.BuyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hftang
 * @date 2019-08-02 11:06
 * @desc
 */
@Controller
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) {
        BuyResult res = tokenService.getUserByToken(token);

        String result = callback + "(" + JsonUtils.objectToJson(res) + ")";
        System.out.println(result);

        return result;
    }

}
