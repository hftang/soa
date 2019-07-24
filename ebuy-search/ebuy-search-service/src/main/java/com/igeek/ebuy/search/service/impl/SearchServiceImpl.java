package com.igeek.ebuy.search.service.impl;

import com.igeek.ebuy.search.mapper.SearchItemMapper;
import com.igeek.ebuy.search.service.SearchService;
import com.igeek.ebuy.search.service.dao.SearchDao;
import com.igeek.ebuy.util.pojo.BuyResult;
import com.igeek.ebuy.util.pojo.SearchItem;
import com.igeek.ebuy.util.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
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
    @Autowired
    private SearchDao searchDao;

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

    /****
     * 查询商品
     * @param page
     * @param rows
     * @param keyword
     * @return
     */

    @Override
    public SearchResult searchItem(int page, int rows, String keyword) {

        //使用solr查询商品

        SolrQuery solrQuery = new SolrQuery();

        //开启高亮
        solrQuery.setHighlight(true);
        solrQuery.set("df", "item_title");
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");

        //开启查询条件
        solrQuery.setQuery("item_title:"+keyword);


        SearchResult searchResult = searchDao.queryItem(solrQuery);

        return searchResult;
    }
}
