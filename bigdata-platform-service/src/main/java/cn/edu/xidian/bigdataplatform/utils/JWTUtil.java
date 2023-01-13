package cn.edu.xidian.bigdataplatform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-20
 * @description: 生成token
 */
@ConfigurationProperties("platform.jwt.config")
@Component
public class JWTUtil {
    private String key;
    private Long ttl;

    /**
     * 生成token
     *
     * @param id   用户id
     * @param name 用户名称
     * @param map  参数
     * @return token
     */
    public String createJwt(String id, String name, Map<String, Object> map) {
        // 设置失效时间
        long l = System.currentTimeMillis();
        long exp = l + ttl;

        // 创建 JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        // 设置claims

        map.forEach(jwtBuilder::claim);
        jwtBuilder.claim("userId", id);

        //        jwtBuilder.setClaims(map);
        jwtBuilder.setExpiration(new Date(exp));
        // 生成token
        return jwtBuilder.compact();
    }

    /**
     * 解析token 获取Claims
     *
     * @param token jwt生成的token
     * @return Claims
     */
    public Claims parseJwt(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
