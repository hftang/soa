package com.igeek.ebuy.item.service.impl;

import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.pojo.TbItemDesc;
import com.igeek.ebuy.service.ItemService;
import com.igeek.ebuy.util.pojo.SearchItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.print.attribute.standard.MediaSize;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hftang
 * @date 2019-07-30 16:14
 * @desc
 */
public class ItemAddListener implements MessageListener {

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    ItemService itemService;

    @Value("${OUT_PATH}")
    private String OUT_PATH;

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
                TbItem item = itemService.queryById(Long.parseLong(itemId.trim()));
                //获取详情
                TbItemDesc itemDesc = itemService.queryItemDescById(item.getId());
                //获取到image
                String[] images = item.getImage().split(",");

                Map map = new HashMap();
                map.put("item", item);
                map.put("itemDesc", itemDesc);
                map.put("images", images);

                Configuration configuration = freeMarkerConfigurer.getConfiguration();
                try {
                    Template template = configuration.getTemplate("item.ftl");

                    Writer out = new FileWriter(new File("D:\\static\\items\\" + item.getId() + ".html"));
                    template.process(map, out);
                    out.close();
                    out = null;

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
