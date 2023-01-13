package cn.edu.xidian.bigdataplatform.mybatis.entity.usersource;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

public class DataSource implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String sourceName;
    private SourceType sourceType;
    private ConnectInfo connectInfo;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime lastModifiedTime;

    public DataSource() {
    }

    public DataSource(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("sourceName") String sourceName,
            @JsonProperty("sourceType") SourceType sourceType,
            @JsonProperty("connectInfo") ConnectInfo connectInfo,
            @JsonProperty("description") String description,
            @JsonProperty("createTime") LocalDateTime createTime,
            @JsonProperty("lastModifiedTime") LocalDateTime lastModifiedTime) {
        this.uuid = uuid;
        this.sourceName = sourceName;
        this.sourceType = sourceType;
        this.connectInfo = connectInfo;
        this.description = description;
        this.createTime = createTime;
        this.lastModifiedTime = lastModifiedTime;
    }

    public static class ConnectInfo implements Serializable{
        HashMap<String, String> options;

        public ConnectInfo() {
        }

        public ConnectInfo(
                @JsonProperty("options") HashMap<String, String> options) {
            this.options = options;
        }

        public HashMap<String, String> getOptions() {
            return options;
        }

        public void setOptions(HashMap<String, String> options) {
            this.options = options;
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public ConnectInfo getConnectInfo() {
        return connectInfo;
    }

    public void setConnectInfo(ConnectInfo connectInfo) {
        this.connectInfo = connectInfo;
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

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
