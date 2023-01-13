package cn.edu.xidian.bigdataplatform.mybatis.entity.search;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LogisticsSource {
    private String indexName;
    private String logisticsId;
    private String published;
    private String depart;
    private String departCompany;
    private String departContact;
    private String departPhone;
    private String departTel;
    private String departQQ;
    private String departFax;
    private String departAddress;
    private String destination;
    private String destinationCompany;
    private String destinationContact;
    private String destinationPhone;
    private String destinationTel;
    private String destinationQQ;
    private String destinationFax;
    private String destinationAddress;
    private String carpoolQuotation;
    private String heavyGoods;
    private String lightGoods;
    private String crawlTime;
}