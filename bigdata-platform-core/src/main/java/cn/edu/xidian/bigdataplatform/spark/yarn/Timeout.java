package cn.edu.xidian.bigdataplatform.spark.yarn;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-08
 * @description:
 */
public class Timeout {

    private String type;
    private String expiryTime;
    private int remainingTimeInSeconds;
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }
    public String getExpiryTime() {
        return expiryTime;
    }

    public void setRemainingTimeInSeconds(int remainingTimeInSeconds) {
        this.remainingTimeInSeconds = remainingTimeInSeconds;
    }
    public int getRemainingTimeInSeconds() {
        return remainingTimeInSeconds;
    }

    public Timeout() {
    }
}