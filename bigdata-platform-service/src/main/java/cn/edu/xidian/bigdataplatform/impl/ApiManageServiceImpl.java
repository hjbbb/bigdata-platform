package cn.edu.xidian.bigdataplatform.impl;

import cn.edu.xidian.bigdataplatform.ApiManageService;
import cn.edu.xidian.bigdataplatform.SearchService;
import cn.edu.xidian.bigdataplatform.mybatis.entity.apimanage.UserApiItem;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.ApiManagerMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiManageServiceImpl implements ApiManageService {

    @Autowired
    ApiManagerMapper apiManagerMapper;

    @Autowired
    RestHighLevelClient client;
    @Autowired
    SearchService searchService;

    @Override
    public UserApiItem getApiByUrl(String url) {
        return apiManagerMapper.getApiByUrl(url);
    }

    @Override
    public UserApiItem getApiById(int itemId) {
        return apiManagerMapper.getApiById(itemId);
    }

    @Override
    public int insertNewApiItem(UserApiItem userApiItem) {
        return apiManagerMapper.insertNewApiItem(userApiItem);
    }

    @Override
    public boolean updateApiItem(UserApiItem userApiItem) {
        return apiManagerMapper.updateApiItem(userApiItem);
    }

    @Override
    public List<UserApiItem> getUserApis(String uuid) {
        return apiManagerMapper.getUserApis(uuid);
    }

    @Override
    public boolean deleteItemById(int id) {
        return apiManagerMapper.deleteItemById(id);
    }

    @Override
    public List<Object> showData(UserApiItem userApiItem, int pageNo, int pageSize) {
        String indicesString = userApiItem.getIndices();
        String[] indices = indicesString.split(",");

        SearchRequest searchRequest = new SearchRequest();
        searchRequest = new SearchRequest(indices);//????????????????????????
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.queryStringQuery("*"));
        setFromSize(sourceBuilder, pageNo, pageSize);
        searchRequest.source(sourceBuilder);
        try{
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);//????????????????????? ??????????????????
            SearchHit[] hits =  searchResponse.getHits().getHits();
            List<Object> sourceList = searchService.getListFromResult(searchResponse);
            return sourceList;

        } catch(Exception e){
            return null;
        }
    }

    private void setFromSize(SearchSourceBuilder sourceBuilder, int pageNo, int pageSize){
        int esFrom=0;//?????????1????????????????????????
        if(pageNo>1){
            //????????????1????????????????????????????????????????????????
            esFrom = (pageNo - 1) * pageSize;
        }
        sourceBuilder.from(esFrom);//????????????????????????
        sourceBuilder.size(pageSize);//??????????????????
    }
}
