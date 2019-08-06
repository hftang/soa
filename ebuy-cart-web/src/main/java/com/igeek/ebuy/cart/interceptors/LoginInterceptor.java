package com.igeek.ebuy.cart.interceptors;

import com.igeek.ebuy.pojo.TbUser;
import com.igeek.ebuy.sso.service.TokenService;
import com.igeek.ebuy.util.cookie.CookieUtils;
import com.igeek.ebuy.util.pojo.BuyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hftang
 * @date 2019-08-06 14:35
 * @desc 拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${TOKEN_COOKIE_NAME}")
    private String TOKEN_COOKIE_NAME;
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //去cookie
        String token = CookieUtils.getCookieValue(httpServletRequest, TOKEN_COOKIE_NAME);
        if (StringUtils.isNotBlank(token)) {
            //从cookie中取出用户信息
            BuyResult result = tokenService.getUserByToken(token);
            if (result.getstatus() == 200) {
                httpServletRequest.setAttribute("loginUser", result.getData());
            }



        }
        return true;//无论是否登录，都放行
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
