package cn.edu.xidian.bigdataplatform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PSpaceVO {
    private String url;
    private int port;
    private String user;
    private String password;
    //
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;
    //
    private String sqlIP;
    private String sqlPort;
    private String sqlUser;
    private String sqlPassword;
    private String dataType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getStart(){return start;}

    public void setStart(Date start){ this.start = start;}

    public Date getEnd(){ return end;}

    public void setEnd(Date end){ this.end = end;}

    public int getPort() {
        return port;
    }

    public void setPort(int port) { this.port = port;}

    public String getUser() {
        return user;
    }

    public void setUser(String user) { this.user = user;}

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSqlIP() { return sqlIP; }

    public void setSqlIP(String sqlIP) {
        this.sqlIP = sqlIP;
    }

    public String getSqlPort() {
        return sqlPort;
    }

    public void setSqlPort(String sqlPort) {
        this.sqlPort = sqlPort;
    }

    public String getSqlUser() {
        return sqlUser;
    }

    public void setSqlUser(String sqlUser) {
        this.sqlUser = sqlUser;
    }

    public String getSqlPassword() { return sqlPassword; }

    public void setSqlPassword(String sqlPassword){ this.sqlPassword = sqlPassword; }

    public String getDataType(){ return dataType; }

    public void setDataType(String dataType){ this.dataType = dataType; }

}
