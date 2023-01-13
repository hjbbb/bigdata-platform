package cn.edu.xidian.bigdataplatform.spark;

import cn.edu.xidian.bigdataplatform.spark.yarn.JsonRootBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-12
 * @description:
 */
@Component
public class SparkHTTPClient {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static String baseURL = "http://127.0.0.1:18081/api/v1/applications";
    private static String yarnHistoryAddress="127.0.0.1";
//    private static String yarnHistoryAddress="192.168.1.4";

    public SparkHTTPClient() {
    }

    public static void main(String[] args) throws IOException {
        // 测试spark history
        //        List<SparkApplication> allApplications = getAllApplications();
//        for (SparkApplication allApplication : allApplications) {
//            System.out.println(allApplication);
//        }
        // 测试yarn history
        JsonRootBean allYarnApplications = getAllYarnApplications();
        System.out.println(allYarnApplications);
    }

    private static OkHttpClient initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        return okHttpClient;
    }

    public static List<SparkApplication> getAllApplications() throws IOException {
        OkHttpClient okHttpClient = initOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("127.0.0.1")
                .port(18081)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("applications")
                .build();

        Request request = new Request.Builder().url(url).get().build();
        Response response = okHttpClient.newCall(request).execute();
        String bodyString = response.body().string();
        List<SparkApplication> sparkApps = objectMapper.readValue(bodyString, new TypeReference<List<SparkApplication>>() {
        });
        return sparkApps;
    }

    public static JsonRootBean getAllYarnApplications() throws IOException {
        OkHttpClient okHttpClient = initOkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(yarnHistoryAddress)
                .port(8088)
                .addPathSegment("ws")
                .addPathSegment("v1")
                .addPathSegment("cluster")
                .addPathSegment("apps")
                .build();

        Request request = new Request.Builder().url(url).get().build();
        Response response = okHttpClient.newCall(request).execute();
        String bodyString = response.body().string();
        JsonRootBean jsonRootBean = objectMapper.readValue(bodyString, new TypeReference<JsonRootBean>() {});
        return jsonRootBean;
    }
}
