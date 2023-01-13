package cn.edu.xidian.bigdataplatform.mybatis.entity.usersource;

import java.util.Date;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-10
 * @description:
 */
public class PSpace {
    private int id;
    private String url;
    private String user;
    private String password;
    private Date start;
    private Date end;
    private String sqlIP;
    private String sqlPort;
    private String sqlUser;
    private String sqlPassword;
    private String dataType;

    public PSpace() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getSqlIP() {
        return sqlIP;
    }

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

    public String getSqlPassword() {
        return sqlPassword;
    }

    public void setSqlPassword(String sqlPassword) {
        this.sqlPassword = sqlPassword;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
