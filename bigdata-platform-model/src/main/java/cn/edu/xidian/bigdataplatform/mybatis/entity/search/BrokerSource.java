package cn.edu.xidian.bigdataplatform.mybatis.entity.search;

import lombok.Data;

//@Data
public class BrokerSource {
    private String indexName;
    private String brokerId;
    private String published;
    private String departPro;
    private String departCity;
    private String departConty;
    private String destinationPro;
    private String destinationCity;
    private String destinationConty;
    private String telNum;
    private String contact;
    private String displayPrize;
    private String carModel;
    private String carLoad;
    private String carLength;
    private String companyName;
    private String remark;
    private String crawlTime;

    public BrokerSource() {
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getDepartPro() {
        return departPro;
    }

    public void setDepartPro(String departPro) {
        this.departPro = departPro;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getDepartConty() {
        return departConty;
    }

    public void setDepartConty(String departConty) {
        this.departConty = departConty;
    }

    public String getDestinationPro() {
        return destinationPro;
    }

    public void setDestinationPro(String destinationPro) {
        this.destinationPro = destinationPro;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationConty() {
        return destinationConty;
    }

    public void setDestinationConty(String destinationConty) {
        this.destinationConty = destinationConty;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDisplayPrize() {
        return displayPrize;
    }

    public void setDisplayPrize(String displayPrize) {
        this.displayPrize = displayPrize;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarLoad() {
        return carLoad;
    }

    public void setCarLoad(String carLoad) {
        this.carLoad = carLoad;
    }

    public String getCarLength() {
        return carLength;
    }

    public void setCarLength(String carLength) {
        this.carLength = carLength;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(String crawlTime) {
        this.crawlTime = crawlTime;
    }
}
