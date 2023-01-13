package cn.edu.xidian.bigdataplatform.spark.yarn;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-08
 * @description:
 */
public class JsonRootBean {

    private Apps apps;
    public void setApps(Apps apps) {
        this.apps = apps;
    }
    public Apps getApps() {
        return apps;
    }

    public JsonRootBean() {
    }
}