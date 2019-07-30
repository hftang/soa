package com.igeek.ebuy.item.controller;

import com.igeek.ebuy.User;
import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.pojo.TbItemDesc;
import com.igeek.ebuy.service.ItemService;
import com.igeek.ebuy.util.pojo.EasyUIDatagridResult;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hftang
 * @date 2019-07-29 16:14
 * @desc
 */
@Controller
public class HtmlGenerateController {

    @Autowired
    FreeMarkerConfigurer createrFreeMarkerConfigurer;
    @Autowired
    ItemService itemService;

    @RequestMapping("/generateAll")
    @ResponseBody
    public String generateAll() throws Exception {

        //读取所有的item
        EasyUIDatagridResult result = itemService.queryByPage(1, 1000);
        List<TbItem> rows = (List<TbItem>) result.getRows();

        Configuration configuration = createrFreeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("item.ftl");

        for (TbItem item : rows) {
            Map data = new HashMap();
            data.put("item", item);
            //放入详情
            TbItemDesc tbItemDesc = itemService.queryItemDescById(item.getId());
            data.put("itemDesc", tbItemDesc);
            data.put("images", item.getImage().split(","));
            FileWriter writer = new FileWriter("D:\\static\\items\\" + item.getId() + ".html");
            template.process(data, writer);
            writer.close();
            writer = null;
        }


        return "ok";
    }

    @RequestMapping("/generateHtml")
    @ResponseBody
    public String generateHtml() throws IOException {
        Configuration configuration = createrFreeMarkerConfigurer.getConfiguration();
        //获取模板
        Template template = configuration.getTemplate("user.ftl");
        //创建对象
        Map data = new HashMap();
        data.put("name", "测试小哈哈");
        User user = new User();
        user.setId(99801);
        user.setName("汤化峰");
        user.setTel("13366678924");
        data.put("u", user);
        //添加集合 users
        List<User> users = new ArrayList<>();
        users.add(new User(1001, "化峰01", "13366678901"));
        users.add(new User(1002, "化峰02", "13366678902"));
        users.add(new User(1003, "化峰03", "13366678903"));
        users.add(new User(1004, "化峰04", "13366678904"));
        data.put("users", users);
        Writer writer = new FileWriter("D:\\test_kk\\hello.html");
        try {
            template.process(data, writer);
            writer.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
