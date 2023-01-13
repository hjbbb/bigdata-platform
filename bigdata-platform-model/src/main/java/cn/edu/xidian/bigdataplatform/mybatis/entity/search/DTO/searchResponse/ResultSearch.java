package cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchResponse;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResultSearch {
    private long totalCount;
    private int pageNo;
    private List<Object> searchList;
    private Map<String,SearchAgg> aggMap;
}
