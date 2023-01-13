package cn.edu.xidian.bigdataplatform.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class propertiesRead {
    @Value("${spring.datasource.url}")
    public String url;
    @Value("${spring.datasource.username}")
    public String username;
    @Value("${spring.datasource.password}")
    public String password;
}
