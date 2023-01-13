package cn.edu.xidian.bigdataplatform.spark;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-12
 * @description:
 */
public class SparkApplicationAttempt {
    private String startTime;
    private String endTime;
    private String lastUpdated;
    private long duration;   // 单位：秒
    private String sparkUser;
    private boolean completed;
    private String appSparkVersion;
    private long startTimeEpoch;
    private long lastUpdatedEpoch;
    private long endTimeEpoch;


    public SparkApplicationAttempt() {
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getSparkUser() {
        return sparkUser;
    }

    public void setSparkUser(String sparkUser) {
        this.sparkUser = sparkUser;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getAppSparkVersion() {
        return appSparkVersion;
    }

    public void setAppSparkVersion(String appSparkVersion) {
        this.appSparkVersion = appSparkVersion;
    }

    public long getStartTimeEpoch() {
        return startTimeEpoch;
    }

    public void setStartTimeEpoch(long startTimeEpoch) {
        this.startTimeEpoch = startTimeEpoch;
    }

    public long getLastUpdatedEpoch() {
        return lastUpdatedEpoch;
    }

    public void setLastUpdatedEpoch(long lastUpdatedEpoch) {
        this.lastUpdatedEpoch = lastUpdatedEpoch;
    }

    public long getEndTimeEpoch() {
        return endTimeEpoch;
    }

    public void setEndTimeEpoch(long endTimeEpoch) {
        this.endTimeEpoch = endTimeEpoch;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"startTime\":\"")
                .append(startTime).append('\"');
        sb.append(",\"endTime\":\"")
                .append(endTime).append('\"');
        sb.append(",\"lastUpdated\":\"")
                .append(lastUpdated).append('\"');
        sb.append(",\"duration\":")
                .append(duration);
        sb.append(",\"sparkUser\":\"")
                .append(sparkUser).append('\"');
        sb.append(",\"completed\":")
                .append(completed);
        sb.append(",\"appSparkVersion\":\"")
                .append(appSparkVersion).append('\"');
        sb.append(",\"startTimeEpoch\":")
                .append(startTimeEpoch);
        sb.append(",\"lastUpdatedEpoch\":")
                .append(lastUpdatedEpoch);
        sb.append(",\"endTimeEpoch\":")
                .append(endTimeEpoch);
        sb.append('}');
        return sb.toString();
    }
}
