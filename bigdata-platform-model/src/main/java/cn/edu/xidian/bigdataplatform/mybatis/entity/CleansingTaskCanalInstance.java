package cn.edu.xidian.bigdataplatform.mybatis.entity;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-14
 * @description:
 */
public class CleansingTaskCanalInstance {
    private int id;
    private int taskId;
    private long canalInstanceId;

    public CleansingTaskCanalInstance() {
    }

    public CleansingTaskCanalInstance(int id, int taskId, long canalInstanceId) {
        this.id = id;
        this.taskId = taskId;
        this.canalInstanceId = canalInstanceId;
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

    public long getCanalInstanceId() {
        return canalInstanceId;
    }

    public void setCanalInstanceId(long canalInstanceId) {
        this.canalInstanceId = canalInstanceId;
    }
}
