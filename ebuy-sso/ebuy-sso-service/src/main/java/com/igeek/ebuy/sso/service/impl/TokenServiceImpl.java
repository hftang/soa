package com.igeek.ebuy.sso.service.impl;

import com.igeek.ebuy.pojo.TbUser;
import com.igeek.ebuy.sso.service.TokenService;
import com.igeek.ebuy.util.jedis.JedisClient;
import com.igeek.ebuy.util.json.JsonUtils;
import com.igeek.ebuy.util.pojo.BuyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;

/**
 * @author hftang
 * @date 2019-08-02 10:27
 * @desc
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    JedisClient jedisClient;

    @Override
    public BuyResult getUserByToken(String token) {

        if (!jedisClient.exists(token)) {
            return BuyResult.build(404, "用户登录过期");
        }
        String json = jedisClient.get(token);
        if (StringUtils.isNotBlank(json)) {
            TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
            //验证成功，刷新下存活时间
            jedisClient.expire(token,60*60);
            return new BuyResult(200, tbUser);
        }
        return BuyResult.build(404, "用户登录过期");
    }
}
