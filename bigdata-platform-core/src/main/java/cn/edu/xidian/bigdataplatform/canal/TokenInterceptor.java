package cn.edu.xidian.bigdataplatform.canal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-24
 * @description:
 */
public class TokenInterceptor implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    private ObjectMapper objectMapper = new ObjectMapper();

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
            logger.debug("token 过期");
            // 发起同步请求获取新token
            String token = CanalAdminHTTPClient.getTokenFromCanalAdmin();
            CanalAdminHTTPClient.canalAdminToken = token;
            logger.debug("token 过期，新token为" + token);
            // 重复请求
            Request refreshRequest = chain.request().newBuilder().removeHeader("X-Token").addHeader("X-Token", token).build();
            return chain.proceed(refreshRequest);
        }
        System.out.println("interceptor return");
        return new Response.Builder()
                .request(response.request())
                .protocol(response.protocol())
                .code(response.code())
                .body(ResponseBody.create(mediaType, responseJson))
                .build();
    }
}
