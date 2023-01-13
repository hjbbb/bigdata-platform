package cn.edu.xidian.bigdataplatform.bean;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class InsertSparkConfig {
    public String backpressure;
    public String initial_rate;
    public String max_rate;

    public int exec(propertiesRead thisSqlProperties){
//        System.out.println(backpressure+" "+initial_rate+" "+max_rate);
        Vector<String> ipAndPort=EncFunc.getIP(thisSqlProperties.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);
        insertSql(ip,port,thisSqlProperties.username,thisSqlProperties.password);
        return status;
    }
    int status=0;

    private void insertSql(String ip,String port, String username,String password){
        Vector<String> databases=ShowFunc.showDatabases(ip,port,username,password);
        if (databases==null){
            status=1;//平台数据库无法链接
        }else {
            if(!databases.contains("platform")){
                try{
                    Driver driver = new com.mysql.cj.jdbc.Driver();
                    DriverManager.registerDriver(driver);
                    String url = "jdbc:mysql://" + ip + ":" + port + "/"+"?characterEncoding=UTF-8";
                    Connection sqlCon=DriverManager.getConnection(url, username, password);
                    String sql="create database platform";
                    sqlCon.prepareStatement(sql).execute();
                    sqlCon.close();
                }catch (SQLException e){
                    e.printStackTrace();
                    status=2;
                }
            }
        }

        Vector<String> tables=ShowFunc.showTables(ip,port,"platform",username,password);
        if (tables==null){
            status=1;//平台数据库无法链接
        }else {
            if(!tables.contains("spark_conf")){
                try{
                    Driver driver = new com.mysql.cj.jdbc.Driver();
                    DriverManager.registerDriver(driver);
                    String url = "jdbc:mysql://" + ip + ":" + port + "/"+"platform"+"?characterEncoding=UTF-8";
                    Connection sqlCon=DriverManager.getConnection(url, username, password);
                    String sql="create table spark_conf(" +
                            "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                            "backpressure varchar(30)," +
                            "initial_rate varchar(20)," +
                            "max_rate varchar(20)" +
                            ")ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";
                    sqlCon.prepareStatement(sql).execute();
                    sqlCon.close();
                }catch (SQLException e){
                    e.printStackTrace();
                    status=2;
                }
            }
        }


        try{
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/"+"platform"+"?characterEncoding=UTF-8";
            Connection sqlCon=DriverManager.getConnection(url, username, password);
            String sql="insert into spark_conf (backpressure,initial_rate,max_rate) values(\""+backpressure+"\",\""+initial_rate+
                  "\",\"" +max_rate+ "\")";
            System.out.println(sql);
            sqlCon.prepareStatement(sql).execute();
            sqlCon.close();
        }catch (SQLException e){
            e.printStackTrace();
            status=2;
        }


    }

    public static void main(String[] args) {
        InsertSparkConfig test=new InsertSparkConfig();
        test.backpressure="true";
        test.initial_rate="10";
        test.max_rate="10";
        test.insertSql("localhost","3306","root","123");
    }

}
