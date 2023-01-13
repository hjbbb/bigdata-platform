package cn.edu.xidian.bigdataplatform.mybatis.entity;

import java.time.LocalDateTime;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-19
 * @description:
 */
public class CleansingTask {
    private Integer id;

    private String taskName;
    private String appId;
    private Integer inputDataSource;
    private String inputType;
    private String inputDataSourceTable;
    private Integer outputTable;
    private LocalDateTime startTime;
    private LocalDateTime completeTime;
    private int status;
    private String publisher;
    private String configLocation;
    private String logLocation;

    public CleansingTask() {
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getOutputTable() {
        return outputTable;
    }

    public void setOutputTable(Integer outputTable) {
        this.outputTable = outputTable;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(LocalDateTime completeTime) {
        this.completeTime = completeTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public Integer getInputDataSource() {
        return inputDataSource;
    }

    public void setInputDataSource(Integer inputDataSource) {
        this.inputDataSource = inputDataSource;
    }

    public String getInputDataSourceTable() {
        return inputDataSourceTable;
    }

    public void setInputDataSourceTable(String inputDataSourceTable) {
        this.inputDataSourceTable = inputDataSourceTable;
    }

    public String getLogLocation() {
        return logLocation;
    }

    public void setLogLocation(String logLocation) {
        this.logLocation = logLocation;
    }
}
