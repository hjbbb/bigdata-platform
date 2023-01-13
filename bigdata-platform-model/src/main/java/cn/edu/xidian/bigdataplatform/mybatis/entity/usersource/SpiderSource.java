package cn.edu.xidian.bigdataplatform.mybatis.entity.usersource;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-08
 * @description:
 */
public class SpiderSource {
    private int id;
    private String name;
    private String website;
    private String description;
    private String sink;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String status;
    private String minute;
    private String hour;
    private String frequency;
    private String value;


    public SpiderSource() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
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
