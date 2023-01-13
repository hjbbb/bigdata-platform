package cn.edu.xidian.bigdataplatform.bean;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class dataxJson{
    public job job;
}
class job{
    public List<readerAndWriter> content;
    public setings setting;
}

class readerAndWriter{
    public reader reader;
    public writer writer;
}
class reader{
    public String name;
    public parameterRead parameter;

}
class writer{
    public String name;
    public parameterWrite parameter;
}
class setings{
    public speed speed;
}
class parameterRead {
    public List<String> column;
    public List<connecttionRead> connection;

    public String password;
    public String username;
    public String where;
}
class parameterWrite {
    public List<String> column;
    public List<connecttionWrite> connection;

    public String autoCreateTable;

    public String password;
    public String username;
    public String where;
    public String writeMode;
}
class connecttionRead {
    public List<String>jdbcUrl;
    public List<String>table;
}
class connecttionWrite {
    public String jdbcUrl;
    public List<String>table;
}

class speed{
    public String  channel="";

}
public class DataxJsonCreate{
    public static String  createJson(String id, String ip, String port, String database, String sourceUsername,String sourcePassword,String tables, String where,List<String>columnNames,String outip,String outport,String platFormUsername,String platFormPassword){
        dataxJson dataxJson = new dataxJson();
        dataxJson.job=new job();
        dataxJson.job.setting=new setings();
//        dataxJson.job.content= List.of(new readerAndWriter());
        // zlx 修改 java8没有List.of
        dataxJson.job.content = Arrays.asList(new readerAndWriter());
        dataxJson.job.content.get(0).reader=new reader();
        dataxJson.job.content.get(0).reader.name="mysqlreader";
        dataxJson.job.content.get(0).reader.parameter=new parameterRead();
        dataxJson.job.content.get(0).reader.parameter.column=new LinkedList<>();
        for (String columnName : columnNames) {
            dataxJson.job.content.get(0).reader.parameter.column.add(columnName);
        }

        dataxJson.job.content.get(0).reader.parameter.connection=new LinkedList<>();
        dataxJson.job.content.get(0).reader.parameter.connection.add(new connecttionRead());

        dataxJson.job.content.get(0).reader.parameter.connection.get(0).jdbcUrl=new LinkedList<>();
        dataxJson.job.content.get(0).reader.parameter.connection.get(0).jdbcUrl.add("jdbc:mysql://"+ip+":"+port+"/"+database+"?characterEncoding=UTF-8&useSSL=false");

        dataxJson.job.content.get(0).reader.parameter.connection.get(0).table=new LinkedList<>();
        dataxJson.job.content.get(0).reader.parameter.connection.get(0).table.add(tables);

        dataxJson.job.content.get(0).reader.parameter.password=sourcePassword;
        dataxJson.job.content.get(0).reader.parameter.username=sourceUsername;
        if(where!=null){
            dataxJson.job.content.get(0).reader.parameter.where=where;
        }

        dataxJson.job.content.get(0).writer=new writer();
        dataxJson.job.content.get(0).writer.name="mysqlwriter";

        dataxJson.job.content.get(0).writer.parameter=new parameterWrite();
        dataxJson.job.content.get(0).writer.parameter.column=new LinkedList<>();
        for (String columnName : columnNames) {
            dataxJson.job.content.get(0).writer.parameter.column.add(columnName);
        }

        dataxJson.job.content.get(0).writer.parameter.connection=new LinkedList<>();
        dataxJson.job.content.get(0).writer.parameter.connection.add(new connecttionWrite());
        dataxJson.job.content.get(0).writer.parameter.connection.get(0).jdbcUrl="jdbc:mysql://"+outip+":"+outport+"/"+database+"_"+id+"?characterEncoding=UTF-8&useSSL=false";

        dataxJson.job.content.get(0).writer.parameter.connection.get(0).table=new LinkedList<>();

        dataxJson.job.content.get(0).writer.parameter.connection.get(0).table.add(tables);


        dataxJson.job.content.get(0).writer.parameter.password=platFormPassword;
        dataxJson.job.content.get(0).writer.parameter.username=platFormUsername;
        dataxJson.job.content.get(0).writer.parameter.writeMode="insert";
//        dataxJson.job.content.get(0).writer.parameter.autoCreateTable="true";

        dataxJson.job.setting.speed=new speed();
        dataxJson.job.setting.speed.channel="1";
        return JSON.toJSONString(dataxJson);
    }

    public static void main(String[] args){

    }
}
