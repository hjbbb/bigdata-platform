package cn.edu.xidian.bigdataplatform.spark;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-12
 * @description:
 */
public class SparkApplication {
    private String id;
    private String name;
    @JsonProperty("attempts")
    private List<SparkApplicationAttempt> sparkApplicationAttempts;

    public SparkApplication() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SparkApplicationAttempt> getSparkApplicationAttempts() {
        return sparkApplicationAttempts;
    }

    public void setSparkApplicationAttempts(List<SparkApplicationAttempt> sparkApplicationAttempts) {
        this.sparkApplicationAttempts = sparkApplicationAttempts;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"sparkApplicationAttempts\":")
                .append(sparkApplicationAttempts);
        sb.append('}');
        return sb.toString();
    }
}
