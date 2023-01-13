package cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchRequest;


import lombok.Data;

@Data
public class RequestSearch {
    private HighFilter highFilter;
    private String keyword;
    private int pageNo;
    private int pageSize;
    private String requestId;
    private SearchSort sortField;
}
