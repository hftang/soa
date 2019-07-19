package com.hftang.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author hftang
 * @date 2019-07-18 16:10
 * @desc
 */
public class SolrJTest {

    /***
     * 添加文档
     * @throws IOException
     * @throws SolrServerException
     */

//    @Test
    public void run01() throws IOException, SolrServerException {

        SolrServer solrServer = new HttpSolrServer("http://192.168.217.154:8080/solr/core1");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 001);
        document.addField("item_title", "solr技术果然吊炸天，天上掉下来 而随着我国排放标准的提高，WEY VV6也不得不进行对应的升级，但这次中期改款的全新VV6，不仅仅满足了国六 B这样严苛的排放要求，对发动机性能也进行了提升，并且在科技配置上也加入了L2级智能驾驶辅助系统以");

        solrServer.add(document);
        solrServer.commit();
    }

    /**
     * 根据id删除文档
     *
     * @throws IOException
     * @throws SolrServerException
     */
//    @Test
    public void run02() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.217.154:8080/solr/core1");
        UpdateResponse updateResponse = solrServer.deleteById("1");
        System.out.println(updateResponse);
        solrServer.commit();
    }

    /***
     * 根据条件删除
     * 删除所有文档是 *：*
     */

//    @Test
    public void run03() throws IOException, SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.217.154:8080/solr/core1");
        String query = "item_title:实用性";
        UpdateResponse updateResponse = httpSolrServer.deleteByQuery(query);
        System.out.println(updateResponse);
        httpSolrServer.commit();
    }

//    @Test
    public void run04() throws SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.217.154:8080/solr/core1");
        SolrQuery querysolr = new SolrQuery();
        querysolr.setQuery("item_title:排放标准");
        QueryResponse response = httpSolrServer.query(querysolr);
        SolrDocumentList results = response.getResults();

        System.out.println("查到的总数是：" + results.getNumFound());

        for (SolrDocument result : results) {

            System.out.println("id:" + result.get("id") + "item_title:" + result.get("item_title"));

        }


    }

    //设置高亮
//    @Test
    public void testHeightLine() throws SolrServerException {

        SolrServer solrServer = new HttpSolrServer("http://192.168.217.154:8080/solr/core1");
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.setQuery("item_title:排放标准");
        solrQuery.setHighlight(true);
        solrQuery.set("df","item_title");
        solrQuery.setHighlightSimplePre("<em style='color=red'>");
        solrQuery.setHighlightSimplePost("</em>");

        QueryResponse response = solrServer.query(solrQuery);
        SolrDocumentList results = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        System.out.println(highlighting);


    }


}
