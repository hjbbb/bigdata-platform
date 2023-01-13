package cn.edu.xidian.bigdataplatform.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

/**
 * @author: Zhou Linxuan
 * @create: 2023-1-4
 * @description:
 */
public class SpiderSourceDTO {
    /**
     * id : 1
     * name : 物通网爬虫
     * website : www.chinawutong.com
     * modifiedTime : 2022/11/15T15:50:00
     * description : 物流数据
     * sink : kafka
     * status : 2
     * minute : 23
     * hour : 17
     * frequency : 2,3,4
     *
     *
     */
    private String id;
    private String name;
    private String website;
    private LocalDateTime createTime;
    private String description;
    private String sink;
    private String status;
    private String minute;
    private String hour;
    private String frequency;
    private String value;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"website\":\"")
                .append(website).append('\"');
        sb.append(",\"createTime\":")
                .append(createTime);
        sb.append(",\"description\":\"")
                .append(description).append('\"');
        sb.append(",\"sink\":\"")
                .append(sink).append('\"');
        sb.append(",\"status\":\"")
                .append(status).append('\"');
        sb.append(",\"minute\":\"")
                .append(minute).append('\"');
        sb.append(",\"hour\":\"")
                .append(hour).append('\"');
        sb.append(",\"frequency\":\"")
                .append(frequency).append('\"');
        sb.append(",\"value\":\"")
                .append(value).append('\"');
        sb.append('}');

        return sb.toString();
    }

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SpiderSourceDTO that = (SpiderSourceDTO) o;

        return new EqualsBuilder().append(id, that.id).append(name, that.name).append(website, that.website).append(createTime, that.createTime).append(description, that.description).append(sink, that.sink).append(status, that.status).append(minute, that.minute).append(hour, that.hour).append(frequency, that.frequency).append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(website).append(createTime).append(description).append(sink).append(status).append(minute).append(hour).append(frequency).append(value).toHashCode();
    }
}
