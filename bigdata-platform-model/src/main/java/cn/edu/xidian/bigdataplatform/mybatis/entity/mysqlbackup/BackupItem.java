package cn.edu.xidian.bigdataplatform.mybatis.entity.mysqlbackup;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class BackupItem {
    private int id;
    private String uuid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String name;
    private String dbName;
    private String description;
    private String type;
    private String host;
    private short port;

    private String username;
    private String password;

    private String periodUnit;
    private int periodFreq;
    private String storagePlace;
    private String status;
    private String message;

    public BackupItem() {
    }

    public BackupItem(int id, String uuid, LocalDateTime date, String name, String dbName, String description, String type, String host, short port, String username, String password, String periodUnit, int periodFreq, String storagePlace, String status, String message) {
        this.id = id;
        this.uuid = uuid;
        this.date = date;
        this.name = name;
        this.dbName = dbName;
        this.description = description;
        this.type = type;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.periodUnit = periodUnit;
        this.periodFreq = periodFreq;
        this.storagePlace = storagePlace;
        this.status = status;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public short getPort() {
        return port;
    }

    public void setPort(short port) {
        this.port = port;
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

    public String getStoragePlace() {
        return storagePlace;
    }

    public void setStoragePlace(String storagePlace) {
        this.storagePlace = storagePlace;
    }

    public String getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
    }

    public int getPeriodFreq() {
        return periodFreq;
    }

    public void setPeriodFreq(int periodFreq) {
        this.periodFreq = periodFreq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
