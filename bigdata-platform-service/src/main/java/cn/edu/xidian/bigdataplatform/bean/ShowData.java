package cn.edu.xidian.bigdataplatform.bean;



import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.MysqlSourceInfo;
import com.alibaba.fastjson.JSON;

import java.util.Arrays;
//import java.util.List;
import java.util.Vector;


public class ShowData {
    public String dataBase,tableName;
    public String field;
    public int status =0;


    public String showDatabases(propertiesRead platformSqlInfo){
        Vector<String> ipAndPort=EncFunc.getIP(platformSqlInfo.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);

        Vector<String>databases=ShowFunc.showDatabases(ip,port,platformSqlInfo.username,platformSqlInfo.password);
        if(databases==null){
            status=1;
        }
        return JSON.toJSONString(databases);
    }

    public String showTables(propertiesRead platformSqlInfo){
        Vector<String> ipAndPort=EncFunc.getIP(platformSqlInfo.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);

        Vector<String>tables=ShowFunc.showTables(ip,port,dataBase,platformSqlInfo.username,platformSqlInfo.password);
        if(tables==null){
            status=2;
        }
        return JSON.toJSONString(tables);
    }
    public String showColumns(propertiesRead platformSqlInfo){
        Vector<String> ipAndPort=EncFunc.getIP(platformSqlInfo.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);

        Vector<String>columns=ShowFunc.showColumnName(ip,port,dataBase,tableName,platformSqlInfo.username,platformSqlInfo.password);
        if(columns==null){
            status=2;
        }
        return JSON.toJSONString(columns);
    }

    public String showSqlData(propertiesRead platformSqlInfo){
        Vector<String> ipAndPort=EncFunc.getIP(platformSqlInfo.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);

        Vector<Vector<String>> sqlData=ShowFunc.showSqlData(ip,port,dataBase,tableName,null,platformSqlInfo.username,platformSqlInfo.password);

        if(sqlData==null){
            status=2;
        }
        return JSON.toJSONString(sqlData);

    }

}
