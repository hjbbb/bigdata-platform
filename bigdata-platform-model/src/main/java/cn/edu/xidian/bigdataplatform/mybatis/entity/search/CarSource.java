package cn.edu.xidian.bigdataplatform.mybatis.entity.search;

import lombok.Data;

@Data
public class CarSource {
    private String indexName;
    private String carId;
    private String residentPro;
    private String residentCity;
    private String telNum;
    private String contact;
    private String licensePlate;
    private String carGPSPro;
    private String carGPSCity;
    private String carGPSArea;
    private String carModel;
    private String carLoad;
    private String carLength;
    private String companyName;
    private String crawlTime;

}
