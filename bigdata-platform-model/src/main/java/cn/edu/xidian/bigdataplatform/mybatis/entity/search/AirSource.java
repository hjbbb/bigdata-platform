package cn.edu.xidian.bigdataplatform.mybatis.entity.search;

import lombok.Data;

@Data
public class AirSource {
    private String indexName;
    private String airId;
    private String depart;
    private String destination;
    private String published;
    private String airCompany;
    private String arrivalTime;
    private String flightSchedule;
    private String transfer;
    private String payment;
    private String fareMin;
    private String fare45;
    private String fare100;
    private String fare300;
    private String fare500;
    private String fare1000;
    private String registration;
    private String companyName;
    private String contact;
    private String location;
    private String address;
    private String crawlTime;
}
