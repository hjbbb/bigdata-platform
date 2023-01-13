package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.mybatis.entity.search.FreightSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchRequest.RequestSearch;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchResponse.ResultSearch;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    List<FreightSource> searchFreightSourceByDepartAndDestination(String depart, String destination, String distance, String freightDescription, String transportType);

    List<FreightSource> findAllFreight();

    public ResultSearch getCommonSearch(RequestSearch requestSearch) throws IOException;
    public long getTotalCount(RequestSearch requestSearch);
    public List<Object> getListFromResult(SearchResponse searchResponse);
    public List<Map> getIndexInfo(String restfulResult);
    public String[] getAllIndices();
}
