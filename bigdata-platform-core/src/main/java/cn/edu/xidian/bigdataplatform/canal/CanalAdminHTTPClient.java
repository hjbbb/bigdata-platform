package cn.edu.xidian.bigdataplatform.canal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-24
 * @description:
 */
public class CanalAdminHTTPClient {
//    public static final String LOGISTICS_DB_PASSWORD = "def312main";
    public static final String LOGISTICS_DB_PASSWORD = "zlx1754wanc";
    public static final String CANAL_ADMIN_DB_USERNAME = "root";
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static Logger logger = LoggerFactory.getLogger(CanalAdminHTTPClient.class);
    public static volatile String canalAdminToken = "";
//    public static String serverClusterId = "server:3";
    // zlx本地
    public static String serverClusterId = "server:5";
    public static String instancePropertyPath = "/usr/local/canal/admin/conf/instance-template.properties";

    /**
     * 同步调用canal admin 登录接口，获取token，在token过期时和应用启动时被调用
     * @return token字符串
     * @throws IOException
     */
    public static String getTokenFromCanalAdmin() throws IOException {
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
        canalAdminToken = (String) ((LinkedHashMap) canalResponse.data).get("token");
        logger.debug(canalAdminToken);
        return canalAdminToken;
    }

    private static OkHttpClient initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        TokenInterceptor tokenInterceptor = new TokenInterceptor();
        okHttpClient.interceptors().add(tokenInterceptor);
        return okHttpClient;
    }

    public static void addTest(int i) throws IOException {
        String dbAddress = "127.0.0.1:3306";
        String wrongDbAddress = "127.0.0.1\\:3306";
        InstanceCreate instance = new InstanceCreate();
        instance.clusterServerId = serverClusterId;
        InputStream is = new FileInputStream("/usr/local/canal/admin/conf/instance-template.properties");
        Properties p = new Properties();
        p.load(is);
        p.setProperty("canal.mq.dynamicTopic", "logistics\\.person");
        p.setProperty("canal.instance.dbUsername", "root");
        p.setProperty("canal.instance.dbPassword", "zlx1754wanc");
        p.setProperty("canal.instance.master.address", "127.0.0.1:3306");
        StringWriter writer = new StringWriter();
        p.store(writer, "");
        String propContent = writer.getBuffer().toString();

        String fixedPropContent = StringUtils.replaceOnce(propContent, wrongDbAddress, dbAddress);
        instance.name = "list测试" + i;
        instance.content = fixedPropContent;
        add(instance);
    }

    public static void main(String[] args) throws IOException {
        // add测试
//        for (int i = 0; i < 22; i++) {
//            addTest(i);
//        }

        // delete测试
//        delete(14);

        // start测试
//        start(13);
//         stop测试
//        stop(13);
        // list测试
//        list();
    }

    public static String delete(Integer instanceId) throws IOException {
        OkHttpClient okHttpClient = initOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instance")
                .addPathSegment(String.valueOf(instanceId))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Token", canalAdminToken)
                .delete()
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String string = response.body().string();
        CanalResponse canalResponse = objectMapper.readValue(string, CanalResponse.class);
        System.out.println(canalResponse.code);
        System.out.println(canalResponse.message);
        System.out.println(canalResponse.data);
        return canalResponse.toString();
    }

    /**
     * 添加instance
     * @param instance
     * @return
     * @throws IOException
     */
    public static String add(InstanceCreate instance) throws IOException {
        OkHttpClient okHttpClient = initOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instance")
                .build();

        String jsonBody = objectMapper.writeValueAsString(instance);
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = okHttpClient.newCall(request).execute();
        String string = response.body().string();
        CanalResponse canalResponse = objectMapper.readValue(string, CanalResponse.class);
        System.out.println(canalResponse.code);
        System.out.println(canalResponse.message);
        System.out.println(canalResponse.data);
        return canalResponse.toString();
    }

    /**
     * 启动instance
     * @return
     */
    public static String start(Integer instanceId) throws IOException {
        OkHttpClient okHttpClient = initOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instance")
                .addPathSegment("status")
                .addPathSegment(String.valueOf(instanceId))
                .addQueryParameter("option", "start")
                .build();
        RequestBody requestBody = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Token", canalAdminToken)
                .put(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        CanalResponse<Boolean> canalResponse = objectMapper.readValue(response.body().string(), new TypeReference<CanalResponse<Boolean>>() {});
        System.out.println(canalResponse.code);
        System.out.println(canalResponse.message);
        System.out.println(canalResponse.data);
        return canalResponse.toString();
    }

    public static String stop(Integer instanceId) throws IOException {
        OkHttpClient okHttpClient = initOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instance")
                .addPathSegment("status")
                .addPathSegment(String.valueOf(instanceId))
                .addQueryParameter("option", "stop")
                .build();
        RequestBody requestBody = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Token", canalAdminToken)
                .put(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        CanalResponse<Boolean> canalResponse = objectMapper.readValue(response.body().string(), new TypeReference<CanalResponse<Boolean>>() {});
        System.out.println(canalResponse.code);
        System.out.println(canalResponse.message);
        System.out.println(canalResponse.data);
        return canalResponse.toString();
    }

    /**
     * 获得实例列表，先请求instances接口获取实例数量，再用这个实例数量去获取所有实例
     * @param
     * @return
     * @throws IOException
     */
    public static CanalResponse<InstanceList> list() throws IOException {
        OkHttpClient okHttpClient = initOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instances")
                .addQueryParameter("name", "")
                .addQueryParameter("clusterServerId", "")
                .addQueryParameter("page", "1")
                .addQueryParameter("size", "20")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Token", canalAdminToken)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        CanalResponse<InstanceList> canalResponse = objectMapper.readValue(response.body().string(), new TypeReference<CanalResponse<InstanceList>>() {});
        System.out.println(canalResponse.code);
        System.out.println(canalResponse.message);
        System.out.println(canalResponse.data);
        System.out.println("----------------------------");

        HttpUrl urlWithAllCount = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instances")
                .addQueryParameter("name", "")
                .addQueryParameter("clusterServerId", "")
                .addQueryParameter("page", "1")
                .addQueryParameter("size", String.valueOf(canalResponse.data.getCount()))
                .build();
        Request requestWithAllCount = new Request.Builder()
                .url(urlWithAllCount)
                .addHeader("X-Token", canalAdminToken)
                .build();
        Response responseWithAllCount = okHttpClient.newCall(requestWithAllCount).execute();
        CanalResponse<InstanceList> canalResponseWithAllCount = objectMapper.readValue(responseWithAllCount.body().string(), new TypeReference<CanalResponse<InstanceList>>() {});
        System.out.println(canalResponseWithAllCount.code);
        System.out.println(canalResponseWithAllCount.message);
        System.out.println(canalResponseWithAllCount.data);
        return canalResponseWithAllCount;
    }

    /**
     * 修改instance
     * @param instanceConfig
     * @return
     * @throws IOException
     */
    public static String save(InstanceConfig instanceConfig) throws IOException {
        OkHttpClient okHttpClient = initOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instance")
                .build();
        String jsonBody = objectMapper.writeValueAsString(instanceConfig);
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Token", canalAdminToken)
                .put(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        CanalResponse<String> data = objectMapper.readValue(response.body().string(), new TypeReference<CanalResponse<String>>() {});
        return data.getData();
    }

    /**
     * 查看instance详情
     * @param instanceId
     * @return
     * @throws IOException
     */
    public static InstanceConfig detail(int instanceId) throws IOException {
        OkHttpClient okHttpClient = initOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(8089)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("canal")
                .addPathSegment("instance")
                .addPathSegment(String.valueOf(instanceId))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Token", canalAdminToken)
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
        return responseObject.getData();
    }
}
