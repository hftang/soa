package com.igeek.ebuy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.ebuy.manager.mapper.TbItemDescMapper;
import com.igeek.ebuy.manager.mapper.TbItemMapper;
import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.pojo.TbItemDesc;
import com.igeek.ebuy.pojo.TbItemExample;
import com.igeek.ebuy.service.ItemService;
import com.igeek.ebuy.util.jedis.JedisClient;
import com.igeek.ebuy.util.json.JsonUtils;
import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.EasyUIDatagridResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author hftang
 * @date 2019-06-24 13:46
 * @desc
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("itemAdd-Destination")
    private Destination activeMQTopicqueue;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TbItem queryById(long itemId) {
        //查询缓存
        String infoKey = "ITEM_INFO:" + itemId + ":BASE";
        String baseInfo = jedisClient.get(infoKey);
        //刷新存活时间
        jedisClient.expire(infoKey, 60 * 60 * 24);

        if (StringUtils.isNotBlank(baseInfo)) {
            return JsonUtils.jsonToPojo(baseInfo, TbItem.class);
        }

        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andIdEqualTo(itemId);


        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);

        if (tbItems != null && tbItems.size() > 0) {
            //将数据存到缓存中
            jedisClient.set(infoKey, JsonUtils.objectToJson(tbItems.get(0)));
            //设置存活时间
            jedisClient.expire(infoKey, 60 * 60 * 24);
            return tbItems.get(0);
        }
        return null;
    }

    //分页查询
    @Override
    public EasyUIDatagridResult queryByPage(int page, int rows) {

        EasyUIDatagridResult result = new EasyUIDatagridResult();

        //分页
        PageHelper.startPage(page, rows);

        TbItemExample tbexample = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(tbexample);

        //设置pageinfo

        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);

        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());


        return result;
    }

    //保存条目
    @Override
    public BuyResult saveItem(TbItem tbItem, String desc) {
        //1 商品条目id
        final String idStr = System.currentTimeMillis() + "" + new Random().nextInt(100);
        long itemId = Long.parseLong(idStr);
        //2 补全商品信息
        tbItem.setId(itemId);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //3 保存tbitem
        tbItemMapper.insert(tbItem);
        //4 保存 desc
        TbItemDesc tbItemDesc = new TbItemDesc();

        tbItemDesc.setItemId(itemId);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemDesc(desc);
        tbItemDescMapper.insert(tbItemDesc);
        //mq 发送消息 把商品的id发送过去就ok 放这里是有问题的 事物有时候没有提交 读取不到
        jmsTemplate.send(activeMQTopicqueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage(idStr);
            }
        });
        return BuyResult.ok(200);
    }

    /***
     * 根据商品id 查询商品的详情
     * @param itemId
     * @return
     */


    @Override
    public TbItemDesc queryItemDescById(long itemId) {
        String descKey = "ITEM_INFO:" + itemId + ":DESC";
        String descInfo = jedisClient.get(descKey);
        //刷新存活时间
        jedisClient.expire(descKey, 60 * 60 * 24);
        //先查
        if (StringUtils.isNotBlank(descInfo)) {
            System.out.println("商品详情从redis中获取");
            return JsonUtils.jsonToPojo(descInfo, TbItemDesc.class);
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        if (tbItemDesc != null) {
            //再 存
            jedisClient.set(descKey, JsonUtils.objectToJson(tbItemDesc));
            //设置存活时间
            jedisClient.expire(descKey, 60 * 60 * 24);
        }
        System.out.println("商品详情从mysql中获取");
        return tbItemDesc;
    }
}
