package cn.edu.xidian.bigdataplatform.bean;

import java.util.Vector;

public class importDatabaseBean {
    public String id;
    public String dbName;
    public String sIp;
    public String sPort;
    public String sourceUsername;
    public String sourcePassword;


    public int exec(propertiesRead thisSqlProperties){
        Vector<String> ipAndPort=EncFunc.getIP(thisSqlProperties.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);
//        String path="/home/corvus/app/datax/job/";
//        String path = "/home/mainuser/app/datax/job/";
        String path = "/home/zlx/app/datax/job/";
//        System.out.println(id +" "+dbName+" "+sIp+" "+sPort+" "+sourceUsername+" "+sourcePassword+" "+ip+" "+port+" "+thisSqlProperties.username
//        +" "+thisSqlProperties.password);
        ImportDatabaseByDatax submit=new ImportDatabaseByDatax(id,dbName,sIp,sPort,sourceUsername,sourcePassword,ip,
                port,thisSqlProperties.username,thisSqlProperties.password,path);

        return submit.status;
    }
}
