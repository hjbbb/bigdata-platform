package cn.edu.xidian.bigdataplatform.canal;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-24
 * @description:
 */
public class NodeServer {
    private Long         id;
    private CanalCluster canalCluster;
    private Long         clusterId;
    private String       name;
    private String       ip;
    private Integer      adminPort;
    private Integer      metricPort;
    private Integer      tcpPort;
    private String       status;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CanalCluster getCanalCluster() {
        return canalCluster;
    }

    public void setCanalCluster(CanalCluster canalCluster) {
        this.canalCluster = canalCluster;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(Integer adminPort) {
        this.adminPort = adminPort;
    }

    public Integer getMetricPort() {
        return metricPort;
    }

    public void setMetricPort(Integer metricPort) {
        this.metricPort = metricPort;
    }

    public Integer getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(Integer tcpPort) {
        this.tcpPort = tcpPort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        sb.append(",\"canalCluster\":")
                .append(canalCluster);
        sb.append(",\"clusterId\":")
                .append(clusterId);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"ip\":\"")
                .append(ip).append('\"');
        sb.append(",\"adminPort\":")
                .append(adminPort);
        sb.append(",\"metricPort\":")
                .append(metricPort);
        sb.append(",\"tcpPort\":")
                .append(tcpPort);
        sb.append(",\"status\":\"")
                .append(status).append('\"');
        sb.append(",\"modifiedTime\":\"")
                .append(modifiedTime).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
