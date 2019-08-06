package com.igeek.ebuy.cart.controller;

import com.igeek.ebuy.cart.service.impl.CartService;
import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.pojo.TbUser;
import com.igeek.ebuy.service.ItemService;
import com.igeek.ebuy.util.cookie.CookieUtils;
import com.igeek.ebuy.util.json.JsonUtils;
import com.igeek.ebuy.util.pojo.BuyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hftang
 * @date 2019-08-05 16:02
 * @desc
 */
@Controller
public class CartController {
    @Value("${CART_NAME}")
    private String CART_NAME;
    @Autowired
    ItemService itemService;
    @Autowired
    CartService cartService;

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCart(@PathVariable long itemId, HttpServletRequest request, HttpServletResponse response) {

        //判断用户是否登录
        Object loginUser = request.getAttribute("loginUser");
        if (loginUser != null) {
            TbUser user = (TbUser) loginUser;
            //已登录
            cartService.deleteCart(user.getId(), itemId);

            return "redirect:/cart/cart.html";
        }

        List<TbItem> carts = getCart(request);
        for (TbItem item : carts) {
            if (itemId == item.getId()) {
                carts.remove(item);
                break;
            }
        }
        //然后再写入cookie
        CookieUtils.setCookie(request, response, CART_NAME, JsonUtils.objectToJson(carts), 60 * 60 * 24 * 7, false);

        return "redirect:/cart/cart.html";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public BuyResult updateNum(@PathVariable long itemId, @PathVariable int num, HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否登录
        Object loginUser = request.getAttribute("loginUser");
        if (loginUser != null) {
            TbUser user = (TbUser) loginUser;
            //已登录
            cartService.updateCart(user.getId(), itemId, num);

            return new BuyResult(200);
        }
        List<TbItem> carts = getCart(request);

        for (TbItem item : carts) {
            if (item.getId() == itemId) {
                item.setNum(num);
                break;
            }
        }
        //将购物车再次写入cookie
        CookieUtils.setCookie(request, response, CART_NAME, JsonUtils.objectToJson(carts), 60 * 60 * 24 * 7, false);

        return new BuyResult(200);
    }

    @RequestMapping("/cart/cart")
    public String cartList(HttpServletRequest request) {
        List<TbItem> cart = getCart(request);
        request.setAttribute("cartList", cart);
        return "cart";
    }

    @RequestMapping("/cart/add/{itemId}")
    public String cartAdd(@PathVariable long itemId, int num, HttpServletRequest request, HttpServletResponse response) {

        //判断用户是否登录

        Object loginUser = request.getAttribute("loginUser");
        if (loginUser != null) {
            TbUser user = (TbUser) loginUser;
            //已登录
            cartService.addCart(user.getId(), itemId, num);

            return "cartSuccess";
        }
        //下面是用户没有登录的操作


        //商品是否已存在
        boolean isExists = false;
        //先从购物车中查询有没有这个产品
        List<TbItem> carts = getCart(request);
        //检查添加的商品是否已存在
        for (TbItem item : carts) {
            if (item.getId() == itemId) {
                //已存在
                item.setNum(item.getNum() + num);
                isExists = true;
                break;
            }
        }
        //不存在添加到购物车中
        if (!isExists) {
            TbItem item = itemService.queryById(itemId);
            item.setNum(num);
            //调整图片
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                image = image.split(",")[0];
                item.setImage(image);
            }
            carts.add(item);
        }
        //写入cookie 设置存活时间 不编码
        CookieUtils.setCookie(request, response, CART_NAME, JsonUtils.objectToJson(carts), 60 * 60 * 24 * 7, false);


        return "cartSuccess";
    }

    private List<TbItem> getCart(HttpServletRequest request) {
        List<TbItem> list = new ArrayList<>();
        String cartJson = CookieUtils.getCookieValue(request, CART_NAME, false);
        if (StringUtils.isNotBlank(cartJson)) {
            list = JsonUtils.jsonToList(cartJson, TbItem.class);
        }
        return list;
    }
}
