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
}
