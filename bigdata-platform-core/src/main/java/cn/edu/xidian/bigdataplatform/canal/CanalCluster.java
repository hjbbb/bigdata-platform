package cn.edu.xidian.bigdataplatform.canal;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-24
 * @description:
 */
public class CanalCluster {
    private Long   id;
    private String name;
    private String zkHosts;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;

    public CanalCluster() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZkHosts() {
        return zkHosts;
    }

    public void setZkHosts(String zkHosts) {
        this.zkHosts = zkHosts;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"zkHosts\":\"")
                .append(zkHosts).append('\"');
        sb.append(",\"modifiedTime\":\"")
                .append(modifiedTime).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
