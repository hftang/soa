package com.igeek.ebuy.cart.service.impl;

import com.igeek.ebuy.manager.mapper.TbItemMapper;
import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.pojo.TbUser;
import com.igeek.ebuy.util.jedis.JedisClient;
import com.igeek.ebuy.util.json.JsonUtils;
import com.igeek.ebuy.util.pojo.BuyResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hftang
 * @date 2019-08-06 15:08
 * @desc
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemMapper itemMapper;

    @Override

    public BuyResult addCart(long userId, long itemId, int num) {

        //先从redis中检查是否存在
        String itemJson = jedisClient.hget(userId + "", itemId + "");
        if (StringUtils.isNotBlank(itemJson)) {
            TbItem item = JsonUtils.jsonToPojo(itemJson, TbItem.class);
            item.setNum(item.getNum() + num);
            //再次放到redis
            jedisClient.hset(userId + "", itemId + "", JsonUtils.objectToJson(item));
        } else {
            //redis不存在
            TbItem item = itemMapper.selectByPrimaryKey(itemId);
            if (item != null) {
                item.setNum(num);
                if (StringUtils.isNotBlank(item.getImage())) {
                    item.setImage(item.getImage().split(",")[0]);
                }
                jedisClient.hset(userId + "", itemId + "", JsonUtils.objectToJson(item));
            }
        }
        return new BuyResult(200);
    }

    @Override
    public BuyResult updateCart(long userId, long itemId, int num) {
        String itemJson = jedisClient.hget(userId + "", itemId + "");
        if (StringUtils.isNotBlank(itemJson)) {
            TbItem item = JsonUtils.jsonToPojo(itemJson, TbItem.class);
            item.setNum(num);
            //然后再次写入到redis
            jedisClient.hset(userId + "", itemId + "", JsonUtils.objectToJson(item));

        }

        return new BuyResult(200);
    }

    @Override
    public BuyResult deleteCart(long userId, long itemId) {
        jedisClient.hdel(userId + "", itemId + "");
        return new BuyResult(200);
    }

    //合并购物车列表
    @Override
    public BuyResult mergeCart(long usrId, List<TbItem> cartList) {
        for (TbItem item : cartList) {
            addCart(usrId, item.getId(), item.getNum());
        }

        return new BuyResult(200);
    }

    //获取服务端购物车的列表
    @Override
    public List<TbItem> getCartList(long userId) {
        List<TbItem> itemList = new ArrayList<>();
        List<String> hvals = jedisClient.hvals(userId + "");
        for (String item : hvals) {
            TbItem item1 = JsonUtils.jsonToPojo(item, TbItem.class);
            itemList.add(item1);
        }
        return itemList;
    }
}
