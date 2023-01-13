package cn.edu.xidian.bigdataplatform.canal;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-12
 * @description:
 */
public class InstanceCreate {
    String clusterServerId;
    String content;
    String name;

    public InstanceCreate() {
    }

    public String getClusterServerId() {
        return clusterServerId;
    }

    public void setClusterServerId(String clusterServerId) {
        this.clusterServerId = clusterServerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
