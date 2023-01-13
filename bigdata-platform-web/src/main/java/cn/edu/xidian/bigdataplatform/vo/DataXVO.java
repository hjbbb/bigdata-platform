package cn.edu.xidian.bigdataplatform.vo;

public class DataXVO {

    private String id;
    private String dbName;
    private String sIp;
    private String sPort;
    private String sourceUsername;
    private String sourcePassword;
    private String pIp;
    private String pPort;
    private String platUserName;
    private String platPassword;

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) { this.dbName = dbName;}

    public String getsIp() { return sIp; }

    public void setsIp(String sIp) {
        this.sIp = sIp;
    }

    public String getsPort() { return sPort; }

    public void setsPort(String sPort) {
        this.sPort = sPort;
    }

    public String getSourceUsername() {
        return sourceUsername;
    }

    public void setSourceUsername(String sourceUsername) {
        this.sourceUsername = sourceUsername;
    }

    public String getSourcePassword() {
        return sourcePassword;
    }

    public void setSourcePassword(String sourcePassword) {
        this.sourcePassword = sourcePassword;
    }

    public String getpIp(){return pIp;}

    public void setpIp(String pIp){this.pIp = pIp;}

    public String getpPort(){ return pPort;}

    public void setpPort(String pPort){this.pPort = pPort; }

    public String getPlatUserName(){return platUserName;}

    public void setPlatUserName(String platUserName){this.platUserName= platUserName;}

    public String getPlatPassword(){return platPassword;}

    public void setPlatPassword(String platPassword){this.platPassword = platPassword;}

}
