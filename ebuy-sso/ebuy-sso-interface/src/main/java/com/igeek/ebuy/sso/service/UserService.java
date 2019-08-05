package com.igeek.ebuy.sso.service;

import com.igeek.ebuy.pojo.TbUser;
import com.igeek.ebuy.util.pojo.BuyResult;

/**
 * @author hftang
 * @date 2019-07-31 16:06
 * @desc
 */
public interface UserService {

    public BuyResult checkData(String data, int type);

    //注册
    public BuyResult register(TbUser user);
    //登录
    public BuyResult login(String userName,String password);
}
