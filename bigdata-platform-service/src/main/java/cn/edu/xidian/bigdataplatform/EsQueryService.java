package cn.edu.xidian.bigdataplatform;


import cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchRequest.RequestSearch;
import org.elasticsearch.index.query.BoolQueryBuilder;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface EsQueryService {
    public SearchSourceBuilder getCommonSearchQuery(RequestSearch requestSearch);
    public BoolQueryBuilder getFilterQuery(RequestSearch requestSearch);
    public void setFromSize(SearchSourceBuilder sourceBuilder, RequestSearch requestSearch);
    public void setSort(SearchSourceBuilder sourceBuilder, RequestSearch requestSearch);
    public SearchSourceBuilder getSearchCountQuery(RequestSearch requestSearch);
}
