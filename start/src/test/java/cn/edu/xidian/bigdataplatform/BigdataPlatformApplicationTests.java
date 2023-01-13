package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.hadoop.HDFSAccessor;
import cn.edu.xidian.bigdataplatform.hadoop.HadoopConnector;
import cn.edu.xidian.bigdataplatform.hadoop.bean.FileStatusBean;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.FreightSource;
import cn.edu.xidian.bigdataplatform.utils.UUIDUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class BigdataPlatformApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(BigdataPlatformApplicationTests.class);

    @Autowired
    HadoopConnector connector;

    @Autowired
    HDFSAccessor accessor;

    @Autowired
    SearchService searchService;

    @Test
    void contextLoads() {
    }

    @Test
    void testHDFSFiles() {
        try {
            connector.init();
            HashMap<String, FileStatusBean> map = new HashMap<>();
            FileStatusBean bean = accessor.getAllDirectoriesRecursivelyFromRoot();
            Stream<FileStatusBean> flatten = flat(bean);
            List<FileStatusBean> collect = flatten.collect(Collectors.toList());
            for (FileStatusBean i : collect) {
                logger.info(String.valueOf(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testHDFSFiles2() {
        try {
            connector.init();
            HashMap<String, FileStatusBean> map = new HashMap<>();
            ArrayList<FileStatusBean> beans = accessor.getAllDirectoriesFromRoot();
            for (FileStatusBean bean : beans) {
                logger.info(String.valueOf(bean));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void genUUID() {
        logger.info(UUIDUtil.genUUID());
    }

    private static Stream<FileStatusBean> flat(FileStatusBean bean) {
        return Stream.concat(Stream.of(bean),
                bean.children.stream().flatMap(BigdataPlatformApplicationTests::flat));
    }

    @Test
    void testEscape() {
        String s = StringEscapeUtils.escapeJava("regexp_replace(depart, '(^\\\\s+|\\\\s+$)','') depart");
        logger.info(s);
    }

    @Test
    void testOkhttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://localhost:18081/applications/local-1668419047961/jobs").build();
    }

//    @Test
//    public void test2(){
//        RestClientBuilder lowLevelRestClient = RestClient.builder(
//                new HttpHost("172.17.0.2", 9200, "http"));
//        RestHighLevelClient client =
//                new RestHighLevelClient(lowLevelRestClient);
//        SearchRequest searchRequest = new SearchRequest("freight");
//        searchRequest.types("account");
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        MatchQueryBuilder mqb = QueryBuilders.matchQuery("", "Virginia");
//        searchSourceBuilder.query(mqb);
//        searchRequest.source(searchSourceBuilder);
//        try {
//            SearchResponse searchResponse = client.search(searchRequest);
//            System.out.println(searchResponse.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    @Test
//    void testFreightSource() {
//        List<FreightSource> freightSources = searchService.searchFreightSourceByDepartAndDestination("河北省-衡水市-武邑县", "山东省-烟台市-福山区", distance, freightDescription, transportType);
//        freightSources.stream().forEach(System.out::println);
//    }
}
