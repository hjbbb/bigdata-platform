package cn.edu.xidian.bigdataplatform.vo;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-18
 * @description:
 */
public class SpiderSourceVO {

    /**
     * id : 1
     * name : 物通网爬虫
     * website : www.chinawutong.com
     * createTime : 2022/11/15T15:50:00
     * description : 物流数据
     * sink : kafka
     * status : 2
     * mini
     *
     * frequncy: d/w/m
     * value: 2,3
     *
     */
    private String id;
    private String name;
    private String website;
    private String createTime;
    private String description;
    private String sink;
    private String status;

    private String minute;
    private String hour;
    private String frequency;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSink() {
        return sink;
    }

    public void setSink(String sink) {
        this.sink = sink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) { this.minute = minute;}

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) { this.hour = hour;}

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) { this.frequency = frequency;}

    public String getValue() {
        return value;
    }

    public void setValue(String value) { this.value = value;}
}
