package cn.edu.xidian.bigdataplatform.mybatis.entity;

import java.time.LocalDateTime;

public class AccessLog {

    private String id;
    private String username;
    private String userid;
    private String operate;
    private LocalDateTime operatetime;
    private String spare;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public LocalDateTime getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(LocalDateTime operatetime) {
        this.operatetime = operatetime;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public AccessLog(String id, String username, String userid, String operate, LocalDateTime operatetime, String spare) {
        this.id = id;
        this.username = username;
        this.userid = userid;
        this.operate = operate;
        this.operatetime = operatetime;
        this.spare = spare;
    }

    public AccessLog() {
    }
}

