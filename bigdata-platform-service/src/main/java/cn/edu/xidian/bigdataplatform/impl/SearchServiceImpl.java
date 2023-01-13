package cn.edu.xidian.bigdataplatform.impl;

import cn.edu.xidian.bigdataplatform.EsQueryService;
import cn.edu.xidian.bigdataplatform.SearchService;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.*;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchRequest.RequestSearch;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchResponse.ResultSearch;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.SearchMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-24
 * @description:
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;

    @Value("${search.db.name}")
    private String dbName;

    @Override
    public List<FreightSource> searchFreightSourceByDepartAndDestination(String depart, String destination, String distance, String freightDescription, String transportType) {
        return searchMapper.queryFreightByDepartAndDestination(dbName, depart, destination, distance, freightDescription, transportType);
    }

    @Override
    public List<FreightSource> findAllFreight() {
        return searchMapper.queryLimited();
    }

    @Autowired
    EsQueryService esQueryService;
    @Autowired
    RestHighLevelClient client;
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public ResultSearch getCommonSearch(RequestSearch requestSearch) throws IOException {
        SearchRequest searchRequest;
        ResultSearch resultSearch = new ResultSearch();
        resultSearch.setPageNo(requestSearch.getPageNo());//设置当前页码
        long searchCount = getTotalCount(requestSearch);//根据搜索条件获取总页数
        resultSearch.setTotalCount(searchCount);
        //开始进行搜索

        if (requestSearch.getKeyword() != null) {
            searchRequest = new SearchRequest("all_indices");//请求所有索引
        } else {
            String[] indexNames = requestSearch.getHighFilter().getIndices();
            if (indexNames[0].equals("*")) {
                searchRequest = new SearchRequest("all_indices");//请求所有索引
            } else {
                searchRequest = new SearchRequest(indexNames);//多个索引进行搜索
            }
        }
        SearchSourceBuilder sourceBuilder = esQueryService.getCommonSearchQuery(requestSearch);//根据搜索条件创造DSL搜索
        searchRequest.source(sourceBuilder);//指定查询条件
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);//搜索的请求对象 请求配置对象
            List<Object> sourceList = getListFromResult(searchResponse);
            resultSearch.setSearchList(sourceList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return resultSearch;
    }

    @Override
    public List<Map> getIndexInfo(String restfulResult) {
        List<Map> maps = new ArrayList<>();
        String[] indicesInfo = restfulResult.split("\n");
        for (String index : indicesInfo) {
            if (index != null) {
                Map<String, Object> indexInfoMap = new HashMap<>();
                String[] infoArray = index.split("\\s+");
                if (!infoArray[2].substring(0, 1).equals(".")) {
                    indexInfoMap.put("health", infoArray[0]);
                    indexInfoMap.put("status", infoArray[1]);
                    indexInfoMap.put("index", infoArray[2]);
                    //indexInfoMap.put("uuid", infoArray[3]);
                    indexInfoMap.put("pri", infoArray[4]);
                    indexInfoMap.put("rep", infoArray[5]);
                    indexInfoMap.put("docs.count", infoArray[6]);
                    indexInfoMap.put("docs.deleted", infoArray[7]);
                    indexInfoMap.put("store.size", infoArray[8]);
                    indexInfoMap.put("pri.store.size", infoArray[9]);
                    maps.add(indexInfoMap);
                }
            }
        }
        return maps;
    }

    public String[] getAllIndices() {
        GetIndexRequest request = new GetIndexRequest().indices("*");
        try {
            GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
            return response.getIndices();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object> getListFromResult(SearchResponse searchResponse) {
        List<Object> searchSourceList = new ArrayList();
        SearchHit[] hits = searchResponse.getHits().getHits();//获取搜索结果集
        for (SearchHit searchHit : hits) {//遍历搜索结果集
            String index = searchHit.getIndex();
            Map<String, Object> sourceMap = searchHit.getSourceAsMap();//_source单条结果转化成key-Value
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();// 解析高亮字段
            if (null != highlightFields) {
                highlightFields.forEach((key, highlightField) -> {//替换高亮字段
                    Text[] fragments = highlightField.getFragments();
                    String newHighlightField = "";
                    for (Text fragment : fragments) {
                        newHighlightField += fragment.toString();
                        //newHighlightField += fragment;
                    }
                    sourceMap.put(key, newHighlightField);
                });
            }
            if (index.equals("logistics")) {
                LogisticsSource logistics = new LogisticsSource();
                logistics.setIndexName(index);
                logistics.setLogisticsId((String) sourceMap.get("logistics_id"));
                logistics.setPublished((String) sourceMap.get("published"));
                logistics.setDepart((String) sourceMap.get("depart"));
                logistics.setDepartCompany((String) sourceMap.get("depart_company"));
                logistics.setDepartContact((String) sourceMap.get("depart_contact"));
                logistics.setDepartPhone((String) sourceMap.get("depart_phone"));
                logistics.setDepartTel((String) sourceMap.get("depart_tel"));
                logistics.setDepartQQ((String) sourceMap.get("depart_QQ"));
                logistics.setDepartFax((String) sourceMap.get("depart_fax"));
                logistics.setDepartAddress((String) sourceMap.get("depart_address"));
                logistics.setDestination((String) sourceMap.get("destination"));
                logistics.setDestinationCompany((String) sourceMap.get("destination_company"));
                logistics.setDestinationContact((String) sourceMap.get("destination_contact"));
                logistics.setDestinationPhone((String) sourceMap.get("destination_phone"));
                logistics.setDestinationTel((String) sourceMap.get("destination_tel"));
                logistics.setDestinationQQ((String) sourceMap.get("destination_QQ"));
                logistics.setDestinationFax((String) sourceMap.get("destination_fax"));
                logistics.setDestinationAddress((String) sourceMap.get("destination_address"));
                logistics.setCarpoolQuotation((String) sourceMap.get("carpool_quotation"));
                logistics.setHeavyGoods((String) sourceMap.get("heavy_goods"));
                logistics.setLightGoods((String) sourceMap.get("light_goods"));
                logistics.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(logistics);
            } else if (index.equals("wutong")) {
                WutongSource wutong = new WutongSource();
                wutong.setIndexName(index);
                wutong.setDepart((String) sourceMap.get("depart"));
                wutong.setDestination((String) sourceMap.get("destination"));
                wutong.setDistance((String) sourceMap.get("distance"));
                wutong.setPublished((String) sourceMap.get("published"));
                wutong.setFreightDescription((String) sourceMap.get("freight_description"));
                wutong.setFreightType((String) sourceMap.get("freight_type"));
                wutong.setTransportType((String) sourceMap.get("transport_type"));
                wutong.setValidity((String) sourceMap.get("validity"));
                wutong.setRemark((String) sourceMap.get("remark"));
                wutong.setVolume((String) sourceMap.get("volume"));
                wutong.setContact((String) sourceMap.get("contact"));
                wutong.setContactNumber((String) sourceMap.get("contact_number"));
                wutong.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(wutong);
            } else if (index.equals("broker")) {
                BrokerSource broker = new BrokerSource();
                broker.setIndexName(index);
                broker.setBrokerId((String) sourceMap.get("broker_id"));
                broker.setPublished((String) sourceMap.get("published"));
                broker.setDepartPro((String) sourceMap.get("depart_pro"));
                broker.setDepartCity((String) sourceMap.get("depart_city"));
                broker.setDepartConty((String) sourceMap.get("depart_conty"));
                broker.setDestinationPro((String) sourceMap.get("destination_pro"));
                broker.setDestinationCity((String) sourceMap.get("destination_city"));
                broker.setDepartConty((String) sourceMap.get("destination_conty"));
                broker.setTelNum((String) sourceMap.get("tel_Num"));
                broker.setContact((String) sourceMap.get("contact"));
                broker.setDisplayPrize((String) sourceMap.get("displayPrize"));
                broker.setCarModel((String) sourceMap.get("car_model"));
                broker.setCarLoad((String) sourceMap.get("car_load"));
                broker.setCarLength((String) sourceMap.get("car_length"));
                broker.setCompanyName((String) sourceMap.get("company_name"));
                broker.setRemark((String) sourceMap.get("remark"));
                broker.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(broker);
            } else if (index.equals("car")) {
                CarSource car = new CarSource();
                car.setIndexName(index);
                car.setResidentCity((String) sourceMap.get("resident_pro"));
                car.setResidentCity((String) sourceMap.get("resident_city"));
                car.setTelNum((String) sourceMap.get("tel_num"));
                car.setContact((String) sourceMap.get("contact"));
                car.setLicensePlate((String) sourceMap.get("license_plate"));
                car.setCarGPSPro((String) sourceMap.get("car_gpspro"));
                car.setCarGPSCity((String) sourceMap.get("car_gpscity"));
                car.setCarLoad((String) sourceMap.get("car_load"));
                car.setCarLength((String) sourceMap.get("car_length"));
                car.setCompanyName((String) sourceMap.get("company_name"));
                car.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(car);
            } else if (index.equals("driver")) {
                DriverSource driver = new DriverSource();
                driver.setIndexName(index);
                driver.setDriverId((String) sourceMap.get("driver_id"));
                driver.setPublished((String) sourceMap.get("published"));
                driver.setDepartPro((String) sourceMap.get("depart_pro"));
                driver.setDepartCity((String) sourceMap.get("depart_city"));
                driver.setDepartConty((String) sourceMap.get("depart_conty"));
                driver.setDestinationPro((String) sourceMap.get("destination_pro"));
                driver.setDestinationCity((String) sourceMap.get("destination_city"));
                driver.setDepartConty((String) sourceMap.get("destination_conty"));
                driver.setTelNum((String) sourceMap.get("tel_Num"));
                driver.setContact((String) sourceMap.get("contact"));
                driver.setDisplayPrize((String) sourceMap.get("displayPrize"));
                driver.setLicensePlate((String) sourceMap.get("license_plate"));
                driver.setCarGPSPro((String) sourceMap.get("car_gpspro"));
                driver.setCarGPSCity((String) sourceMap.get("car_gpscity"));
                driver.setCarGPSArea((String) sourceMap.get("car_gpsarea"));
                driver.setCarModel((String) sourceMap.get("car_model"));
                driver.setCarLoad((String) sourceMap.get("car_load"));
                driver.setCarLength((String) sourceMap.get("car_length"));
                driver.setCompanyName((String) sourceMap.get("company_name"));
                driver.setRemark((String) sourceMap.get("remark"));
                driver.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(driver);
            } else if (index.equals("air")) {
                AirSource air = new AirSource();
                air.setIndexName(index);
                air.setAirId((String) sourceMap.get("air_id"));
                air.setDepart((String) sourceMap.get("depart"));
                air.setDestination((String) sourceMap.get("destination"));
                air.setPublished((String) sourceMap.get("published"));
                air.setAirCompany((String) sourceMap.get("air_company"));
                air.setArrivalTime((String) sourceMap.get("arrival_time"));
                air.setFlightSchedule((String) sourceMap.get("flight_schedule"));
                air.setTransfer((String) sourceMap.get("transfer"));
                air.setPayment((String) sourceMap.get("payment"));
                air.setFareMin((String) sourceMap.get("fare_Min"));
                air.setFare45((String) sourceMap.get("fare_45"));
                air.setFare100((String) sourceMap.get("fare_100"));
                air.setFare300((String) sourceMap.get("fare_300"));
                air.setFare500((String) sourceMap.get("fare_500"));
                air.setFare1000((String) sourceMap.get("fare_1000"));
                air.setRegistration((String) sourceMap.get("registration"));
                air.setCompanyName((String) sourceMap.get("company_name"));
                air.setContact((String) sourceMap.get("contact"));
                air.setLocation((String) sourceMap.get("location"));
                air.setAddress((String) sourceMap.get("address"));
                air.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(air);
            } else if (index.equals("fcl")) {
                FclSource fcl = new FclSource();
                fcl.setIndexName(index);
                fcl.setFclId((String) sourceMap.get("fcl_id"));
                fcl.setDepart((String) sourceMap.get("depart"));
                fcl.setDestination((String) sourceMap.get("destination"));
                fcl.setPublished((String) sourceMap.get("published"));
                fcl.setCompanyName((String) sourceMap.get("company_name"));
                fcl.setRegistration((String) sourceMap.get("registration"));
                fcl.setContact((String) sourceMap.get("contact"));
                fcl.setLocation((String) sourceMap.get("location"));
                fcl.setAddress((String) sourceMap.get("address"));
                fcl.setCarrier((String) sourceMap.get("carrier"));
                fcl.setSail((String) sourceMap.get("sail"));
                fcl.setBoxType((String) sourceMap.get("box_type"));
                fcl.setEntreport((String) sourceMap.get("entreport"));
                fcl.setDeparture((String) sourceMap.get("departure"));
                fcl.setBl((String) sourceMap.get("BL"));
                fcl.setPayment((String) sourceMap.get("payment"));
                fcl.setFare20((String) sourceMap.get("fare_20"));
                fcl.setFare40((String) sourceMap.get("fare_40"));
                fcl.setFare40HQ((String) sourceMap.get("fare_40HQ"));
                fcl.setFare45((String) sourceMap.get("fare_45"));
                fcl.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(fcl);
            } else if (index.equals("lcl")) {
                LclSource lcl = new LclSource();
                lcl.setIndexName(index);
                lcl.setLclId((String) sourceMap.get("lcl_id"));
                lcl.setDepart((String) sourceMap.get("depart"));
                lcl.setDestination((String) sourceMap.get("destination"));
                lcl.setCarrier((String) sourceMap.get("carrier"));
                lcl.setPublished((String) sourceMap.get("published"));
                lcl.setSail((String) sourceMap.get("sail"));
                lcl.setBoxType((String) sourceMap.get("box_type"));
                lcl.setEntreport((String) sourceMap.get("entreport"));
                lcl.setDeparture((String) sourceMap.get("departure"));
                lcl.setBl((String) sourceMap.get("BL"));
                lcl.setPayment((String) sourceMap.get("payment"));
                lcl.setFareTon((String) sourceMap.get("fare_ton"));
                lcl.setFareCube((String) sourceMap.get("fare_cube"));
                lcl.setRegistration((String) sourceMap.get("registration"));
                lcl.setCompanyName((String) sourceMap.get("company_name"));
                lcl.setContact((String) sourceMap.get("contact"));
                lcl.setLocation((String) sourceMap.get("location"));
                lcl.setAddress((String) sourceMap.get("address"));
                lcl.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(lcl);
            } else if (index.equals("bulk")) {
                BulkSource bulk = new BulkSource();
                bulk.setIndexName(index);
                bulk.setBulkId((String) sourceMap.get("lcl_id"));
                bulk.setDepart((String) sourceMap.get("depart"));
                bulk.setDestination((String) sourceMap.get("destination"));
                bulk.setPublished((String) sourceMap.get("published"));
                bulk.setDeliveryType((String) sourceMap.get("delivery_type"));
                bulk.setCargoType((String) sourceMap.get("cargo_type"));
                bulk.setShipType((String) sourceMap.get("ship_type"));
                bulk.setFareTon((String) sourceMap.get("fare_ton"));
                bulk.setRegistration((String) sourceMap.get("registration"));
                bulk.setCompanyName((String) sourceMap.get("company_name"));
                bulk.setContact((String) sourceMap.get("contact"));
                bulk.setLocation((String) sourceMap.get("location"));
                bulk.setAddress((String) sourceMap.get("address"));
                bulk.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(bulk);
            } else if (index.equals("land")) {
                LandSource land = new LandSource();
                land.setIndexName(index);
                land.setLandId((String) sourceMap.get("land_id"));
                land.setDepart((String) sourceMap.get("depart"));
                land.setDestination((String) sourceMap.get("destination"));
                land.setPublished((String) sourceMap.get("published"));
                land.setDeliveryType((String) sourceMap.get("delivery_type"));
                land.setCarType((String) sourceMap.get("car_type"));
                land.setCarLength((String) sourceMap.get("car_length"));
                land.setFareHeavy((String) sourceMap.get("fare_heavy"));
                land.setFareLight((String) sourceMap.get("fare_light"));
                land.setRegistration((String) sourceMap.get("registration"));
                land.setCompanyName((String) sourceMap.get("company_name"));
                land.setContact((String) sourceMap.get("contact"));
                land.setLocation((String) sourceMap.get("location"));
                land.setAddress((String) sourceMap.get("address"));
                land.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(land);
            } else if (index.equals("rail")) {
                RailSource rail = new RailSource();
                rail.setIndexName(index);
                rail.setRailId((String) sourceMap.get("rail_id"));
                rail.setDepart((String) sourceMap.get("depart"));
                rail.setDestination((String) sourceMap.get("destination"));
                rail.setPublished((String) sourceMap.get("published"));
                rail.setWayside((String) sourceMap.get("way_side"));
                rail.setDeliveryType((String) sourceMap.get("delivery_type"));
                rail.setCarType((String) sourceMap.get("car_type"));
                rail.setCarLoad((String) sourceMap.get("car_load"));
                rail.setCarSize((String) sourceMap.get("car_size"));
                rail.setPayment((String) sourceMap.get("payment"));
                rail.setFareKilo((String) sourceMap.get("fare_kilo"));
                rail.setRegistration((String) sourceMap.get("registration"));
                rail.setCompanyName((String) sourceMap.get("company_name"));
                rail.setContact((String) sourceMap.get("contact"));
                rail.setLocation((String) sourceMap.get("location"));
                rail.setAddress((String) sourceMap.get("address"));
                rail.setCrawlTime((String) sourceMap.get("crawl_time"));
                searchSourceList.add(rail);
            }
        }
        return searchSourceList;
    }

    @Override
    public long getTotalCount(RequestSearch requestSearch) {
        CountRequest countRequest;

        if (requestSearch.getKeyword() != null) {
            countRequest = new CountRequest("all_indices");
        } else {
            String[] indexNames = requestSearch.getHighFilter().getIndices();
            if (indexNames[0].equals("*")) {
                countRequest = new CountRequest("all_indices");
            } else {
                countRequest = new CountRequest(indexNames);//客户端count请求
            }
        }
        SearchSourceBuilder searchSourceBuilder = esQueryService.getSearchCountQuery(requestSearch);
        countRequest.source(searchSourceBuilder);//设置查询
        try {
            CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);//执行count
            return countResponse.getCount();//返回count结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
