package com.igeek.ebuy.content.service.impl;

import com.igeek.ebuy.content.service.ContentService;
import com.igeek.ebuy.manager.mapper.ContentExMapper;
import com.igeek.ebuy.manager.mapper.TbContentMapper;
import com.igeek.ebuy.pojo.TbContent;
import com.igeek.ebuy.pojo.TbContentExample;
import com.igeek.ebuy.util.jedis.JedisClient;
import com.igeek.ebuy.util.json.JsonUtils;
import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.EasyUIDatagridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author hftang
 * @date 2019-07-12 10:50
 * @desc
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private ContentExMapper contentExMapper;

    @Autowired
    private JedisClient jedisClient;


    @Override
    public BuyResult saveContent(TbContent tbContent) {

        //补全信息
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());

        tbContentMapper.insert(tbContent);

        //同步缓存操作下面

        String key = "CONTENT:" + tbContent.getCategoryId();
        //删除操作
        jedisClient.del(key);



        return BuyResult.ok(200);
    }

    //查询所有
    @Override
    public EasyUIDatagridResult queryContent(long categoryId, int page, int rows) {

        Map map = new HashMap();
        map.put("categoryId", categoryId);
        map.put("start", (page - 1) * rows);
        map.put("size", rows);
        List<TbContent> list = contentExMapper.queryByPage(map);
        int count = contentExMapper.queryCount(map);

        EasyUIDatagridResult easyUIDatagridResult = new EasyUIDatagridResult();

        easyUIDatagridResult.setRows(list);
        easyUIDatagridResult.setTotal(count);

        return easyUIDatagridResult;


    }

    /**
     * 查询轮播图
     *
     * @param categoryId
     * @return
     */

    @Override
    public List<TbContent> queryByCategoryId(long categoryId) {
        //设计id
        String key = "CONTENT:" + categoryId;
        //获取值
        String json = jedisClient.get(key);
        if (StringUtils.isNoneBlank(json)) {
            //有数据，直接放回
            //将json转换为集合返回。
            List<TbContent> tbContents = JsonUtils.jsonToList(json, TbContent.class);
            System.out.println("从redis中获取：" + tbContents);
            Logger.getGlobal().info("从redis中获取：" + tbContents);
            return tbContents;
        }
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        List<TbContent> list = tbContentMapper.selectByExample(example);
        System.out.println("从数据库中获取：" + list);
        Logger.getGlobal().info("从数据库中获取：" + list);
        //保存一份到redis中
        jedisClient.set(key, JsonUtils.objectToJson(list));
        return list;
    }
}
