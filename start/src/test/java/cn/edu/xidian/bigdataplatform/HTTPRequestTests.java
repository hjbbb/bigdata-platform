package cn.edu.xidian.bigdataplatform;

import cn.hutool.core.util.EscapeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.squareup.okhttp.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.*;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-24
 * @description:
 */
@SpringBootTest
public class HTTPRequestTests {
    private static final Logger logger = LoggerFactory.getLogger(HTTPRequestTests.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    class User {
        Long   id;
        String username;
        String password;
        String roles;
        String introduction;
        String avatar;
        String name;
        Date creationDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(Date creationDate) {
            this.creationDate = creationDate;
        }
    }

    static class CanalResponse<T> {
        public CanalResponse() {
        }

        int code;
        String message;
        T data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    class TestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            ResponseBody originalBody = response.body();
            MediaType mediaType;
            String responseJson = response.body().string();

            if (originalBody != null && originalBody.contentType() != null) {
                mediaType = response.body().contentType();
            } else {
                mediaType = MediaType.parse("application/json;charset=utf-8");
            }

//            {"code":50014,"message":"Expired token","data":null}
            CanalResponse canalResponse = objectMapper.readValue(responseJson, CanalResponse.class);
            if (canalResponse.code == 50014) {
                // TODO 发起同步请求获取新token
                String token = getTokenFromCanalAdmin();
                // 重复请求
                Request refreshRequest = chain.request().newBuilder().removeHeader("X-Token").addHeader("X-Token", token).build();
                return chain.proceed(refreshRequest);
            }
            return new Response.Builder().body(ResponseBody.create(mediaType, responseJson)).build();
        }
    }

    @Test
    void HuToolAndCanalAdminTest() throws IOException {
        User user = new User();
        user.username = "admin";
        user.password = "123456";
        String jsonBody = objectMapper.writeValueAsString(user);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder().url("http://127.0.0.1:8089/api/v1/user/login").post(requestBody).build();
        Response response = okHttpClient.newCall(request).execute();
        Headers headers = response.headers();
        Map<String, List<String>> responseHeaderMap = headers.toMultimap();
        for (String key : responseHeaderMap.keySet()) {
            logger.debug(key);
            logger.debug(StringUtils.join(responseHeaderMap.get(key)));
        }
        ResponseBody body = response.body();
        String bodyString = body.string();
        logger.debug(bodyString);
    }


    String getTokenFromCanalAdmin() throws IOException {
        User user = new User();
        user.username = "admin";
        user.password = "123456";
        String jsonBody = objectMapper.writeValueAsString(user);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder().url("http://127.0.0.1:8089/api/v1/user/login").post(requestBody).build();
        Response response = okHttpClient.newCall(request).execute();
        String string = response.body().string();
        CanalResponse canalResponse = objectMapper.readValue(string, CanalResponse.class);
        String token = (String) ((LinkedHashMap) canalResponse.data).get("token");
        logger.debug(token);
        return token;
    }

    @Test
    void HuToolAndCanalAdminTestInstances() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = getTokenFromCanalAdmin();


        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instances")
                .addQueryParameter("page", "1")
                .addQueryParameter("size","20")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Token", token)
                .get()
                .build();
        Response response = okHttpClient.newCall(request).execute();
        Headers headers = response.headers();
        Map<String, List<String>> responseHeaderMap = headers.toMultimap();
        for (String key : responseHeaderMap.keySet()) {
            logger.debug(key);
            logger.debug(StringUtils.join(responseHeaderMap.get(key)));
        }
        ResponseBody body = response.body();
        String bodyString = body.string();
        logger.debug(bodyString);
    }

    static class CanalCluster {
        private Long   id;
        private String name;
        private String zkHosts;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        private Date   modifiedTime;

        public CanalCluster() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getZkHosts() {
            return zkHosts;
        }

        public void setZkHosts(String zkHosts) {
            this.zkHosts = zkHosts;
        }

        public Date getModifiedTime() {
            return modifiedTime;
        }

        public void setModifiedTime(Date modifiedTime) {
            this.modifiedTime = modifiedTime;
        }
    }

    static class NodeServer {
        private Long         id;
        private CanalCluster canalCluster;
        private Long         clusterId;
        private String       name;
        private String       ip;
        private Integer      adminPort;
        private Integer      metricPort;
        private Integer      tcpPort;
        private String       status;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        private Date         modifiedTime;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public CanalCluster getCanalCluster() {
            return canalCluster;
        }

        public void setCanalCluster(CanalCluster canalCluster) {
            this.canalCluster = canalCluster;
        }

        public Long getClusterId() {
            return clusterId;
        }

        public void setClusterId(Long clusterId) {
            this.clusterId = clusterId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getAdminPort() {
            return adminPort;
        }

        public void setAdminPort(Integer adminPort) {
            this.adminPort = adminPort;
        }

        public Integer getMetricPort() {
            return metricPort;
        }

        public void setMetricPort(Integer metricPort) {
            this.metricPort = metricPort;
        }

        public Integer getTcpPort() {
            return tcpPort;
        }

        public void setTcpPort(Integer tcpPort) {
            this.tcpPort = tcpPort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Date getModifiedTime() {
            return modifiedTime;
        }

        public void setModifiedTime(Date modifiedTime) {
            this.modifiedTime = modifiedTime;
        }
    }

    static class InstanceConfig {
        private Long         id;
        private Long         clusterId;
        private CanalCluster         canalCluster;
        private Long         serverId;
        private NodeServer         nodeServer;
        private String       name;
        private String       content;
        private String       contentMd5;
        private String       status;             // 1: 正常 0: 停止
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        private Date         modifiedTime;
        private String       clusterServerId;
        private String       runningStatus = "0"; // 1: 运行中 0: 停止

        public InstanceConfig() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getClusterId() {
            return clusterId;
        }

        public void setClusterId(Long clusterId) {
            this.clusterId = clusterId;
        }


        public Long getServerId() {
            return serverId;
        }

        public void setServerId(Long serverId) {
            this.serverId = serverId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentMd5() {
            return contentMd5;
        }

        public void setContentMd5(String contentMd5) {
            this.contentMd5 = contentMd5;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Date getModifiedTime() {
            return modifiedTime;
        }

        public void setModifiedTime(Date modifiedTime) {
            this.modifiedTime = modifiedTime;
        }

        public String getClusterServerId() {
            return clusterServerId;
        }

        public void setClusterServerId(String clusterServerId) {
            this.clusterServerId = clusterServerId;
        }

        public String getRunningStatus() {
            return runningStatus;
        }

        public void setRunningStatus(String runningStatus) {
            this.runningStatus = runningStatus;
        }

        public NodeServer getNodeServer() {
            return nodeServer;
        }

        public void setNodeServer(NodeServer nodeServer) {
            this.nodeServer = nodeServer;
        }

        public CanalCluster getCanalCluster() {
            return canalCluster;
        }

        public void setCanalCluster(CanalCluster canalCluster) {
            this.canalCluster = canalCluster;
        }
    }

    @Test
    void HuToolAndCanalAdminTestInstanceByID() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        TestInterceptor testInterceptor = new TestInterceptor();
        okHttpClient.interceptors().add(testInterceptor);
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instance")
                .addPathSegment("4")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Token", "fake-token")
                .get()
                .build();
        Response response = okHttpClient.newCall(request).execute();
        Headers headers = response.headers();
        Map<String, List<String>> responseHeaderMap = headers.toMultimap();
        for (String key : responseHeaderMap.keySet()) {
            logger.debug(key);
            logger.debug(StringUtils.join(responseHeaderMap.get(key)));
        }
        ResponseBody body = response.body();
        String bodyString = body.string();
        logger.debug(bodyString);
        CanalResponse<InstanceConfig> responseObject = objectMapper.readValue(bodyString, new TypeReference<CanalResponse<InstanceConfig>>() {
        });
        String content = responseObject.getData().getContent();
        Properties p = new Properties();
        p.load(new StringReader(content));
        String value = "logistics\\.person";
        p.setProperty("canal.mq.dynamicTopic", value);
        logger.debug(p.toString());
    }

    @Test
    void parseContent() throws IOException {
        InputStream is = new FileInputStream("/usr/local/canal/admin/conf/instance-template.properties");
        String content = "# canal.instance.mysql.slaveId=0\\n\\n# enable gtid use true/false\\ncanal.instance.gtidon=false\\n\\n# position info\\ncanal.instance.master.address=127.0.0.1:3306\\ncanal.instance.master.journal.name=\\ncanal.instance.master.position=\\ncanal.instance.master.timestamp=\\ncanal.instance.master.gtid=\\n\\n# rds oss binlog\\ncanal.instance.rds.accesskey=\\ncanal.instance.rds.secretkey=\\ncanal.instance.rds.instanceId=\\n\\n# table meta tsdb info\\ncanal.instance.tsdb.enable=true\\n#canal.instance.tsdb.url=jdbc:mysql://127.0.0.1:3306/canal_tsdb\\n#canal.instance.tsdb.dbUsername=canal\\n#canal.instance.tsdb.dbPassword=canal\\n\\n#canal.instance.standby.address =\\n#canal.instance.standby.journal.name =\\n#canal.instance.standby.position =\\n#canal.instance.standby.timestamp =\\n#canal.instance.standby.gtid=\\n\\n# username/password\\ncanal.instance.dbUsername=root\\ncanal.instance.dbPassword=zlx1754wanc\\ncanal.instance.connectionCharset = UTF-8\\n# enable druid Decrypt database password\\ncanal.instance.enableDruid=false\\n#canal.instance.pwdPublicKey=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALK4BUxdDltRRE5/zXpVEVPUgunvscYFtEip3pmLlhrWpacX7y7GCMo2/JM6LeHmiiNdH1FWgGCpUfircSwlWKUCAwEAAQ==\\n\\n# table regex\\ncanal.instance.filter.regex=.*\\\\\\\\..*\\n# table black regex\\ncanal.instance.filter.black.regex=\\n# table field filter(format: schema1.tableName1:field1/field2,schema2.tableName2:field1/field2)\\n#canal.instance.filter.field=test1.t_product:id/subject/keywords,test2.t_company:id/name/contact/ch\\n# table field black filter(format: schema1.tableName1:field1/field2,schema2.tableName2:field1/field2)\\n#canal.instance.filter.black.field=test1.t_product:subject/product_image,test2.t_company:id/name/contact/ch\\n\\n# mq config\\ncanal.mq.topic=canal-example\\n# dynamic topic route by schema or table regex\\ncanal.mq.dynamicTopic=logistics\\\\\\\\.freight_source\\ncanal.mq.partition=0\\n# hash partition config\\n#canal.mq.partitionsNum=3\\n#canal.mq.partitionHash=test.table:id^name,.*\\\\\\\\..*\\n";
        logger.debug(content);
        final Properties p = new Properties();
        p.load(new StringReader(content.replaceAll("\\n", ";")));
        logger.debug("e");
    }
}
