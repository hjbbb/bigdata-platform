package cn.edu.xidian.bigdataplatform.impl;

import cn.edu.xidian.bigdataplatform.EsQueryService;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchRequest.Cond;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchRequest.RequestSearch;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EsQueryServiceImpl implements EsQueryService {

    @Autowired
    RestHighLevelClient client;

    @Override
    public SearchSourceBuilder getCommonSearchQuery(RequestSearch requestSearch){
        BoolQueryBuilder boolQueryBuilder = getFilterQuery(requestSearch);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder
                .requireFieldMatch(false)
                .field("*")
                .preTags("<span style='color:red;'>")
                .postTags("</span>");//高亮显示查询结果
        sourceBuilder.query(boolQueryBuilder)
                .highlighter(highlightBuilder)
                .timeout(TimeValue.timeValueSeconds(50));
        setSort(sourceBuilder, requestSearch);//构建排序规则
        setFromSize(sourceBuilder, requestSearch);//构建翻页请求
        return sourceBuilder;
    }

    /** @Override
    public SearchSourceBuilder getHighSearchQuery(RequestSearch requestSearch) {
        BoolQueryBuilder boolQueryBuilder = getFilterQuery(requestSearch);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.encoder("utf-8")
                .preTags("<span style='color:red;'>").postTags("</span>");
        sourceBuilder.query(boolQueryBuilder)
                .highlighter(highlightBuilder)
                .timeout(TimeValue.timeValueSeconds(50));
        setSort(sourceBuilder, requestSearch);//构建排序规则
        setFromSize(sourceBuilder, requestSearch);//构建翻页请求
        return sourceBuilder;
    }
    **/


    @Override
    public BoolQueryBuilder getFilterQuery(RequestSearch requestSearch) {
        BoolQueryBuilder conjBoolQueryBuilder = new BoolQueryBuilder();
        if (requestSearch.getKeyword() != null) {
            String keyword = requestSearch.getKeyword();
            conjBoolQueryBuilder.must(QueryBuilders.queryStringQuery(keyword));
        } else {
            Cond[] conds = requestSearch.getHighFilter().getConds();
            for (Cond cond : conds) {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                boolQueryBuilder.must(QueryBuilders.queryStringQuery("*"));
                String field = cond.getFields();
                String option = cond.getOption();
                String content = cond.getContent();
                String conj = cond.getConj();
                if (option.equals("textInclude")) {
                    boolQueryBuilder.must(QueryBuilders.queryStringQuery(content).field(field));
                }else if (option.equals("keyInclude")) {
                    boolQueryBuilder.must(QueryBuilders.queryStringQuery("*"+content+"*").field(field));
                }else if (option.equals("keyIncludes")) {
                    BoolQueryBuilder innerShouldBool = new BoolQueryBuilder();
                    String[] keys = content.split("OR");
                    for (String key : keys) {
                        if (!key.equals("")) {
                            innerShouldBool.should(QueryBuilders.queryStringQuery("*" + key + "*").field(field));
                        }
                        boolQueryBuilder.must(innerShouldBool);
                    }
                }else if (option.equals("exclude")) {
                    BoolQueryBuilder innerBool = new BoolQueryBuilder();
                    BoolQueryBuilder innerMustBool = new BoolQueryBuilder();
                    String[] keys = content.split("-");
                    for (String key : keys) {
                        if (!key.equals("")) {
                            innerBool.should(QueryBuilders.queryStringQuery("*" + key + "*").field(field));
                        }
                    }
                    innerMustBool.must(innerBool);
                    boolQueryBuilder.mustNot(innerMustBool);
                }
                if(conj.equals("and")){
                    conjBoolQueryBuilder.must(boolQueryBuilder);
                 }
                else if(conj.equals("or")){
                    conjBoolQueryBuilder.should(boolQueryBuilder);
                }
            }
        }
        return conjBoolQueryBuilder;
    }

    @Override
    public void setSort(SearchSourceBuilder sourceBuilder, RequestSearch requestSearch){
        /*
        if(requestSearch.getSortField()!=null){
            String sortField = requestSearch.getSortField().getSortField();
            String sortType = requestSearch.getSortField().getSortType();
            if("asc".equals(sortType)){
                sourceBuilder.sort(sortField, SortOrder.ASC);
            }
            if("desc".equals(sortType)){
                sourceBuilder.sort(sortField,SortOrder.DESC);
            }
        }*/
    }


    @Override
    public void setFromSize(SearchSourceBuilder sourceBuilder, RequestSearch requestSearch){
        int pageSize = requestSearch.getPageSize();
        int pageNo = requestSearch.getPageNo();
        int esFrom=0;//页码为1时的翻页起始位置
        if(pageNo>1){
            //页码不为1时，翻页的位置为当前页的起始位置
            esFrom = (pageNo - 1) * pageSize;
        }
        sourceBuilder.from(esFrom);//设置翻页起始位置
        sourceBuilder.size(pageSize);//设置翻页大小
    }


    @Override
    public SearchSourceBuilder getSearchCountQuery(RequestSearch requestSearch){
        BoolQueryBuilder boolQuery = getFilterQuery(requestSearch);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();//创建搜索builder
        sourceBuilder.query(boolQuery);
        return  sourceBuilder;
    }
}
