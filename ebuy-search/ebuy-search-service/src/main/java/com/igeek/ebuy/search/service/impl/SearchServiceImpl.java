package com.igeek.ebuy.search.service.impl;

import com.igeek.ebuy.search.mapper.SearchItemMapper;
import com.igeek.ebuy.search.service.SearchService;
import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.SearchItem;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hftang
 * @date 2019-07-19 9:50
 * @desc
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public BuyResult importIndex() {

        List<SearchItem> itemList = searchItemMapper.getItemList();
        List<SolrInputDocument> docs = new ArrayList<>();


        for (SearchItem item : itemList) {
            //创建文档
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", item.getId());
            doc.addField("item_title", item.getTitle());
            doc.addField("item_sell_point", item.getSell_point());
            doc.addField("item_price", item.getPrice());
            doc.addField("item_category_name", item.getCategory_name());
            //处理图片
            String image = item.getItem_image();
            String[] images = image.split(",");
            if (images != null && images.length > 0) {
                image = images[0];
            }
            doc.addField("item_image", image);

            docs.add(doc);

        }

        try {
            solrServer.add(docs);
            solrServer.commit();
            return new BuyResult(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BuyResult(400);

    }
}
