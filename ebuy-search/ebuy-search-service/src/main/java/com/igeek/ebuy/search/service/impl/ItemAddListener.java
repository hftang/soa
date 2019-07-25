package com.igeek.ebuy.search.service.impl;

import com.igeek.ebuy.search.mapper.SearchItemMapper;
import com.igeek.ebuy.search.service.dao.SearchDao;
import com.igeek.ebuy.util.pojo.SearchItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author hftang
 * @date 2019-07-24 17:03
 * @desc
 */
public class ItemAddListener implements MessageListener {
    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private SearchDao searchDao;

    @Override
    public void onMessage(Message message) {
        //先延时100s
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        TextMessage msg = (TextMessage) message;
        try {
            String itemId = msg.getText();
            if (StringUtils.isNotBlank(itemId)) {
                SearchItem item = searchItemMapper.getItemById(Long.parseLong(itemId));
                searchDao.saveItem(item);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
