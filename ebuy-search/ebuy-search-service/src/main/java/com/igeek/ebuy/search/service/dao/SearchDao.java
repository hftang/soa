package com.igeek.ebuy.search.service.dao;

import com.igeek.ebuy.util.pojo.SearchItem;
import com.igeek.ebuy.util.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hftang
 * @date 2019-07-22 13:59
 * @desc 查询索引库中的 dao
 */
@Repository
public class SearchDao {
    @Autowired
    private SolrServer solrServer;
    public SearchResult queryItem(SolrQuery query) {
        SearchResult searchResult = new SearchResult();
        try {
            QueryResponse response = solrServer.query(query);
            SolrDocumentList results = response.getResults();
            //设置总条数
            searchResult.setRecourdCount((int) results.getNumFound());
            List<SearchItem> list = new ArrayList<>();
            //取出高亮的
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            for (SolrDocument item : results) {
                SearchItem searchItem = new SearchItem();
                searchItem.setId(item.get("id").toString());
                searchItem.setCategory_name(item.get("item_category_name").toString());
                searchItem.setItem_image(item.get("item_image").toString());
                searchItem.setPrice(Long.parseLong(item.get("item_price").toString()));
                searchItem.setSell_point(item.get("item_sell_point").toString());
                searchItem.setTitle(item.get("item_title").toString());
                //判断高亮显示是否有结果
                if(highlighting!=null){
                    Map<String, List<String>> map = highlighting.get(searchItem.getId());
                    List<String> item_title = map.get("item_title");
                    if(item_title!=null&&item_title.size()>0){
                        searchItem.setTitle(item_title.get(0));
                    }
                }
                list.add(searchItem);
            }
            searchResult.setItemList(list);
        } catch (SolrServerException e) {
            throw new RuntimeException(e);
        }
        return searchResult;
    }


}
