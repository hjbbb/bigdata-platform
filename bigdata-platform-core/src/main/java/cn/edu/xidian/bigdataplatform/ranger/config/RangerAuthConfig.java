package cn.edu.xidian.bigdataplatform.ranger.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
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
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class RangerAuthConfig {
    @Value("${ranger.username:admin}")
    private String username;
    @Value("${ranger.password:admin}")
    private String password;

    @Override
    public String toString() {
        return "RangerAuthConfig{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}