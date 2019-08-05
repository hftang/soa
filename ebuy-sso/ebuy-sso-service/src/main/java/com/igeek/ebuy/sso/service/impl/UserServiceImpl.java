package com.igeek.ebuy.sso.service.impl;

import com.igeek.ebuy.util.jedis.JedisClient;
import com.igeek.ebuy.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import com.igeek.ebuy.manager.mapper.TbUserMapper;
import com.igeek.ebuy.pojo.TbUser;
import com.igeek.ebuy.pojo.TbUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.igeek.ebuy.sso.service.UserService;
import com.igeek.ebuy.util.pojo.BuyResult;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author hftang
 * @date 2019-07-31 16:07
 * @desc
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    TbUserMapper userMapper;
    @Autowired
    JedisClient jedisClient;


    @Override
    public BuyResult checkData(String data, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //判断一下设置条件类型
        if (type == 1) {
            criteria.andUsernameEqualTo(data);
        }
        if (type == 2) {
            criteria.andPhoneEqualTo(data);
        }
        BuyResult result = new BuyResult(200);

        List<TbUser> users = userMapper.selectByExample(example);

        if (users != null && users.size() > 0) {
            result.setData(false);
        } else {
            result.setData(true);
        }


        return result;
    }

    @Override
    public BuyResult register(TbUser user) {
        //先校验一番
        if (StringUtils.isBlank(user.getUsername())) {
            return BuyResult.build(400, "用户名为空，注册失败！");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return BuyResult.build(400, "密码为空，注册失败！");
        }
        if (StringUtils.isBlank(user.getPhone())) {
            return BuyResult.build(400, "手机为空，注册失败！");
        }
        if (!(boolean) (checkData(user.getUsername(), 1).getData())) {
            return BuyResult.build(400, "用户名重复，注册失败！");
        }
        if (!(boolean) (checkData(user.getPhone(), 2).getData())) {
            return BuyResult.build(400, "手机号码重复，注册失败！");
        }
        //补全数据
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码加密
        String pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(pwd);
        //保存用户
        int insert = userMapper.insert(user);
        if (insert == 1) {
            return new BuyResult(200);
        } else {
            return BuyResult.build(400, "注册失败！");
        }


    }

    //用户的登录
    @Override
    public BuyResult login(String userName, String password) {
        String pwd = null;
        if (StringUtils.isNotBlank(password)) {
            pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        }
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(userName);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            criteria.andPhoneEqualTo(userName);
            list = userMapper.selectByExample(example);
        }
        if (list == null || list.size() == 0) {
            //表示登录失败
            return BuyResult.build(400, "用户名或者密码错误");
        }


        TbUser user = list.get(0);
        String pwd2 = user.getPassword();
        if (pwd2.equals(pwd)) {
            BuyResult buyResult = new BuyResult(200);

            //存入session
            String token = UUID.randomUUID().toString().replace("-", "");
            token = "SESSION:" + token;
            //存入redis模拟session
            jedisClient.set(token, JsonUtils.objectToJson(user));
            //设置存活时间
            jedisClient.expire(token, 60 * 60);

            buyResult.setData(200);
            buyResult.setMsg(token);

            return buyResult;
        }
        return BuyResult.build(400, "登录密码错误");

    }
}
