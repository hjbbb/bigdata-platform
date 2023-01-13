package cn.edu.xidian.bigdataplatform.canal;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-24
 * @description:
 */
public class InstanceConfig {
    private Long         id;
    private Long         clusterId;
    private CanalCluster         canalCluster;
    private Long         serverId;
    private NodeServer         nodeServer;
    private String       name;
    private String       content;
    private String       contentMd5;
    private String       status;             // 1: 正常 0: 停止
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
    private String       clusterServerId;
    private String       runningStatus = "0"; // 1: 运行中 0: 停止

    public InstanceConfig() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }


    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentMd5() {
        return contentMd5;
    }

    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
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

    public String getClusterServerId() {
        return clusterServerId;
    }

    public void setClusterServerId(String clusterServerId) {
        this.clusterServerId = clusterServerId;
    }

    public String getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(String runningStatus) {
        this.runningStatus = runningStatus;
    }

    public NodeServer getNodeServer() {
        return nodeServer;
    }

    public void setNodeServer(NodeServer nodeServer) {
        this.nodeServer = nodeServer;
    }

    public CanalCluster getCanalCluster() {
        return canalCluster;
    }

    public void setCanalCluster(CanalCluster canalCluster) {
        this.canalCluster = canalCluster;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"clusterId\":")
                .append(clusterId);
        sb.append(",\"canalCluster\":")
                .append(canalCluster);
        sb.append(",\"serverId\":")
                .append(serverId);
        sb.append(",\"nodeServer\":")
                .append(nodeServer);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"content\":\"")
                .append(content).append('\"');
        sb.append(",\"contentMd5\":\"")
                .append(contentMd5).append('\"');
        sb.append(",\"status\":\"")
                .append(status).append('\"');
        sb.append(",\"modifiedTime\":\"")
                .append(modifiedTime).append('\"');
        sb.append(",\"clusterServerId\":\"")
                .append(clusterServerId).append('\"');
        sb.append(",\"runningStatus\":\"")
                .append(runningStatus).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
