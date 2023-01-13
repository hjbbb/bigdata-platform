package cn.edu.xidian.bigdataplatform.mybatis.entity;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-14
 * @description:
 */
public class CleansingTaskSparkApp {
    private int id;
    private int taskId;
    private String sparkAppId;

    public CleansingTaskSparkApp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getSparkAppId() {
        return sparkAppId;
    }

    public void setSparkAppId(String sparkAppId) {
        this.sparkAppId = sparkAppId;
    }
}
