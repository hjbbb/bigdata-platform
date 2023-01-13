package cn.edu.xidian.bigdataplatform.mybatis.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * env {
 *   # You can set spark configuration here
 *   # see available properties defined by spark: https://spark.apache.org/docs/latest/configuration.html#available-properties
 *   spark.app.name = "SeaTunnel"
 *   spark.executor.instances = 2
 *   spark.executor.cores = 1
 *   spark.executor.memory = "1g"
 *   spark.streaming.batchDuration = 5
 * }
 */
public class Env extends PluginConfig{

    private HashMap<String, Object> sparkAppProperties;

    public Env() {
        this.sparkAppProperties = new HashMap<>();
        // 默认
        setProperty("spark.app.name", "SeaTunnel");
        setProperty("spark.executor.instances", 2);
        setProperty("spark.executor.cores", 1);
        setProperty("spark.executor.memory", "1g");
    }

    public void setProperty(String propertyName, Object value) {
        sparkAppProperties.put(propertyName, value);
    }

    @Override
    public String toConfig(int indent) {
        final StringBuilder sb = new StringBuilder();
        sb.append("env {\n");
        for (Map.Entry<String, Object> property : sparkAppProperties.entrySet()) {
            sb.append(StringUtils.repeat("\t", indent))
                    .append(property.getKey())
                    .append(" = ")
                    .append(StringUtils.wrap(String.valueOf(property.getValue()), "\""))
                    .append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }
}
