package cn.edu.xidian.bigdataplatform.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-18
 * @description:
 */
public class SpiderTaskDTO {
    /**
     * id : 1
     * name : 物通网爬虫
     * website : www.chinawutong.com
     * modifiedTime : 2022/11/15T15:50:00
     * description : 物流数据
     * sink : kafka
     * status : 2
     */
    private String id;
    private String name;
    private String website;
    private LocalDateTime modifiedTime;
    private String description;
    private String sink;
    private String status;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"website\":\"")
                .append(website).append('\"');
        sb.append(",\"modifiedTime\":")
                .append(modifiedTime);
        sb.append(",\"description\":\"")
                .append(description).append('\"');
        sb.append(",\"sink\":\"")
                .append(sink).append('\"');
        sb.append(",\"status\":\"")
                .append(status).append('\"');
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

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SpiderTaskDTO that = (SpiderTaskDTO) o;

        return new EqualsBuilder().append(id, that.id).append(name, that.name).append(website, that.website).append(modifiedTime, that.modifiedTime).append(description, that.description).append(sink, that.sink).append(status, that.status).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(website).append(modifiedTime).append(description).append(sink).append(status).toHashCode();
    }
}
