package cn.edu.xidian.bigdataplatform.mybatis.entity.search;

import lombok.Data;

@Data
public class BulkSource {
    private String indexName;
    private String bulkId;
    private String depart;
    private String destination;
    private String published;
    private String deliveryType;
    private String cargoType;
    private String shipType;
    private String fareTon;
    private String registration;
    private String companyName;
    private String contact;
    private String location;
    private String address;
    private String crawlTime;
}
