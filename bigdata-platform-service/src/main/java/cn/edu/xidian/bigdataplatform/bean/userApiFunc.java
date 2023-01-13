package cn.edu.xidian.bigdataplatform.bean;


import cn.edu.xidian.bigdataplatform.SearchService;
import cn.edu.xidian.bigdataplatform.mybatis.entity.apimanage.UserApiItem;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.client.RestHighLevelClient;


import org.elasticsearch.action.search.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Vector;

public class userApiFunc {
    public String showData(propertiesRead thisSqlProperties,String url){
        Vector<String> ipAndPort=EncFunc.getIP(thisSqlProperties.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);
        UserApiControl check =new UserApiControl();
        if(check.apiCheck(ip,port,thisSqlProperties.username,thisSqlProperties.password,"/userApi/"+url))
            return null;
        String indexName=url.split("_")[1];
        System.out.println(indexName);


        return null;
    }

    @Autowired
    RestHighLevelClient client;
    @Autowired
    SearchService searchService;

    public String  showData(propertiesRead thisSqlProperties, String url, Integer pageNo) {
        Vector<String> ipAndPort = EncFunc.getIP(thisSqlProperties.url);
        String ip = ipAndPort.get(0);
        String port = ipAndPort.get(1);
        UserApiControl check = new UserApiControl();
        if (check.apiCheck(ip, port, thisSqlProperties.username, thisSqlProperties.password, "/userApi/" + url))
            return null;
        String indexName=url.split("_")[1];

        SearchRequest searchRequest = new SearchRequest();
        if (indexName.equals("*")){
            searchRequest = new SearchRequest("all_indices");//请求所有索引
        }else {
            searchRequest = new SearchRequest(indexName);//多个索引进行搜索
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.queryStringQuery("*"));
        setFromSize(sourceBuilder, pageNo);
        searchRequest.source(sourceBuilder);
        try{
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);//搜索的请求对象 请求配置对象
            SearchHit[] hits =  searchResponse.getHits().getHits();
            List<Object> sourceList = searchService.getListFromResult(searchResponse);
            return JSON.toJSONString(sourceList);

        } catch(Exception e){
            return null;
        }
    }

    public String  showData(UserApiItem userApiItem, int pageNo, int pageSize) {
        String indicesString = userApiItem.getIndices();
        String[] indices = indicesString.split(",");

        SearchRequest searchRequest = new SearchRequest();
        searchRequest = new SearchRequest(indices);//多个索引进行搜索
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.queryStringQuery("*"));
        setFromSize(sourceBuilder, pageNo, pageSize);
        searchRequest.source(sourceBuilder);
        try{
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);//搜索的请求对象 请求配置对象
            SearchHit[] hits =  searchResponse.getHits().getHits();
            List<Object> sourceList = searchService.getListFromResult(searchResponse);
            return JSON.toJSONString(sourceList);

        } catch(Exception e){
            return null;
        }
    }

    public void setFromSize(SearchSourceBuilder sourceBuilder, int pageNo){
        int pageSize = 20;
        int esFrom=0;//页码为1时的翻页起始位置
        if(pageNo>1){
            //页码不为1时，翻页的位置为当前页的起始位置
            esFrom = (pageNo - 1) * pageSize;
        }
        sourceBuilder.from(esFrom);//设置翻页起始位置
        sourceBuilder.size(pageSize);//设置翻页大小
    }

    public void setFromSize(SearchSourceBuilder sourceBuilder, int pageNo, int pageSize){
        int esFrom=0;//页码为1时的翻页起始位置
        if(pageNo>1){
            //页码不为1时，翻页的位置为当前页的起始位置
            esFrom = (pageNo - 1) * pageSize;
        }
        sourceBuilder.from(esFrom);//设置翻页起始位置
        sourceBuilder.size(pageSize);//设置翻页大小
    }

}
