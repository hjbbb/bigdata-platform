package cn.edu.xidian.bigdataplatform.mybatis.entity.search;

import lombok.Data;

@Data
public class RailSource {
    private String indexName;
    private String railId;
    private String depart;
    private String destination;
    private String published;
    private String wayside;
    private String deliveryType;
    private String carType;
    private String carLoad;
    private String carSize;
    private String payment;
    private String fareKilo;
    private String registration;
    private String companyName;
    private String contact;
    private String location;
    private String address;
    private String crawlTime;
}
