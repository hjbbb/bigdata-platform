package cn.edu.xidian.bigdataplatform.mybatis.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Maskpojo {
    private  String uuid;
    private String id;
    private String name;
    private String des;
    private String host;
    private String port;
    private String user;
    private String password;
    private String schema;
    private String table;
    private Integer maskway;
    private Integer savetype;

    private  String maskwaytext;

    private  String savetypetext;

    private LocalDateTime createtime;

    public Maskpojo() {
    }

    public Maskpojo( String name, String des, String host, String port, String user, String password, String schema, String table, Integer maskway, Integer savetype) {
        this.name = name;
        this.des = des;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.schema = schema;
        this.table = table;
        this.maskway = maskway;
        this.savetype = savetype;
    }
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }


    public String getMaskwaytext() {
        return maskwaytext;
    }

    public void setMaskwaytext(String maskwaytext) {
        this.maskwaytext = maskwaytext;
    }

    public String getSavetypetext() {
        return savetypetext;
    }

    public void setSavetypetext(String savetypetext) {
        this.savetypetext = savetypetext;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public String getUuid() {
    return uuid;
}

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Integer getMaskway() {
        return maskway;
    }

    public void setMaskway(Integer maskway) {
        this.maskway = maskway;
    }

    public Integer getSavetype() {
        return savetype;
    }

    public void setSavetype(Integer savetype) {
        this.savetype = savetype;
    }

    @Override
    public String toString() {
        return "Maskpojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", schema='" + schema + '\'' +
                ", table='" + table + '\'' +
                ", maskway=" + maskway +
                ", savetype=" + savetype +
                '}';
    }
}
