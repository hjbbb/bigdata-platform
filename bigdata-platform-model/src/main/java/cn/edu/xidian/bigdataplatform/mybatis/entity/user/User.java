package cn.edu.xidian.bigdataplatform.mybatis.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-20
 * @description: 用户实体类
 */
public class User {
    public String uuid;
    public String username;
    public String password;
    public String pk;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pkCreateTime;

    public User() {
    }

    public User(String uuid, String username, String password, String pk, LocalDateTime pkCreateTime) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.pk = pk;
        this.pkCreateTime = pkCreateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public LocalDateTime getPkCreateTime() {
        return pkCreateTime;
    }

    public void setPkCreateTime(LocalDateTime pkCreateTime) {
        this.pkCreateTime = pkCreateTime;
    }
}
