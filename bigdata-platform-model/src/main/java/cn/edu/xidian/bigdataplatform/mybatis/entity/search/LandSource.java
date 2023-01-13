package cn.edu.xidian.bigdataplatform.mybatis.entity.search;

import lombok.Data;

@Data
public class LandSource {
    private String indexName;
    private String landId;
    private String depart;
    private String destination;
    private String published;
    private String deliveryType;
    private String carType;
    private String carLength;
    private String fareHeavy;
    private String fareLight;
    private String registration;
    private String companyName;
    private String contact;
    private String location;
    private String address;
    private String crawlTime;
}
