package com.igeek.ebuy.sso.service;

import com.igeek.ebuy.util.pojo.BuyResult;

/**
 * @author hftang
 * @date 2019-08-02 10:25
 * @desc 验证用户登录
 */
public interface TokenService {

    //根据token返回用户信息
    public BuyResult getUserByToken(String token);
}
