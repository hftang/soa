package com.igeek.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.ebuy.manager.mapper.TbItemMapper;
import com.igeek.ebuy.pojo.TbItem;
import com.igeek.ebuy.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author hftang
 * @date 2019-06-26 15:19
 * @desc pagehelper的测试
 */
public class PageHelperTest {


//    @Test
    public void testPageHelper() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");

        TbItemMapper itemMapper = context.getBean(TbItemMapper.class);

        //设置分页
        PageHelper.startPage(1, 10);

        TbItemExample example = new TbItemExample();

        List<TbItem> tbItems = itemMapper.selectByExample(example);

        //使用pageinfo封装查询结果
        PageInfo pageInfo = new PageInfo(tbItems);

        long total = pageInfo.getTotal();
        int size = pageInfo.getSize();

        System.out.println("总共查询了多少条" + total);
        System.out.println("总共查询了size:" + size);

        for (TbItem item : tbItems) {
            System.out.println(item.getId() + ":" + item.getTitle());
        }


    }


}
