package com.igeek.ebuy;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hftang
 * @date 2019-07-26 16:00
 * @desc
 */
public class test {

//    @Test
    public void run01() throws IOException {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("D:\\javaweb\\ebuy-parent\\ebuy-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("user.ftl");
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


    }
}
