package cn.edu.xidian.bigdataplatform.ranger.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import feign.Logger;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author suman.bn
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RangerClientConfig {

    @Value("${ranger.connectTimeoutMillis:5000}")
    private int connectTimeoutMillis;
    @Value("${ranger.readTimeoutMillis:10000}")
    private int readTimeoutMillis;
    private Logger.Level logLevel = Logger.Level.FULL;

    @Value("${ranger.url:http://localhost:6080}")
    private String url = "http://localhost:6080";

    @Autowired
    private RangerAuthConfig authConfig;

    @Override
    public String toString() {
        return "RangerClientConfig{" +
                "connectTimeoutMillis=" + connectTimeoutMillis +
                ", readTimeoutMillis=" + readTimeoutMillis +
                ", logLevel=" + logLevel +
                ", url='" + url + '\'' +
                ", authConfig=" + authConfig +
                '}';
    }

}
