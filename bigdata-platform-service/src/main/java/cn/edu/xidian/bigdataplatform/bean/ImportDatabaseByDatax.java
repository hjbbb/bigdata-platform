package cn.edu.xidian.bigdataplatform.bean;

import java.io.*;
import java.sql.*;
import java.util.*;

public class ImportDatabaseByDatax {
    private Connection con_sourceDB = null;
    private Connection con_backup = null;
    // metaData of db
    private DatabaseMetaData metaData = null;
    public int status=0;
    // num of column---end with "," or ")"

    // table in order
    private final LinkedHashSet<String> tableInOrder = new LinkedHashSet<>();
    // instructions
    Vector<String>json=new Vector<>();
/*
    public static void main(String[] args) {
        ImportDatabaseByDatax test=new ImportDatabaseByDatax("1","sxemp-boot","47.108.138.67", "13306",
                "xdu_read","XDU@admin123","localhost","3306","root","root",
                "G:\\dataX\\job");
    }*/
    public ImportDatabaseByDatax(String id, String dbName, String sIp, String sPort, String sourceUsername, String sourcePassword,
                                 String pIp, String pPort, String platUsername, String platPassword, String path)  {

        connect(id,dbName,sIp,sPort,sourceUsername,sourcePassword,pIp,pPort,platUsername,platPassword);// connect to sqlite
        getTablesAndSort();// output: sorted_tables
        for (String tableName : tableInOrder) {
            createTables(tableName);
            // zlx java8没有List.of
            //            List<String>column=List.of("*");
            List<String> column = Arrays.asList("*");
            String TmpJson=DataxJsonCreate.createJson(id,sIp,sPort,dbName,sourceUsername,sourcePassword,tableName,
                    null,column,pIp,pPort,platUsername,platPassword);
            json.add(TmpJson);
            System.out.println(TmpJson);
        }
        try{
            if(con_sourceDB!=null){
                con_sourceDB.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        try{
            if(con_backup!=null){
                con_backup.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        for(int i=0;i<json.size();i++){
            int finalI = i;
            Thread tmp= new Thread(() -> exec(json.get(finalI), path+finalI +".json"));
            tmp.start();
            try{
                tmp.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }
    private void exec(String json,String path)  {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(path));
            os.write(json.getBytes(), 0, json.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert os != null;
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String exexcPath=path+"../bin/datax.py";
//            System.out.println("commmend:"+commend);
//            ProcessBuilder pb = new ProcessBuilder("python", "/home/corvus/app/datax/job/../bin/datax.py",path);
            ProcessBuilder pb = new ProcessBuilder("python", "/usr/local/datax/bin/datax.py",path);
            Process process = pb.start();
            process.waitFor();
            File file = new File(path);
            if (file.delete()) {
                System.out.println("File deleted successfully");
            }
            else {
                System.out.println("Failed to delete the file");
            }

        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

    }
    private void connect(String id, String dbName,String sIp,String sPort,
                         String sourceUsername,String sourcePassword,String pIp,String pPort,String platUsername,String platPassword) {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            con_sourceDB = DriverManager.getConnection("jdbc:mysql://" + sIp + ":" + sPort + "/"+dbName+"?characterEncoding=UTF-8&nullCatalogMeansCurrent=true&serverTimezone=Asia/Shanghai",sourceUsername,sourcePassword); // connect to the source database

            Connection con=DriverManager.getConnection("jdbc:mysql://" + pIp + ":" + pPort + "/"+"?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",platUsername,platPassword);
            String sql="create database `"+dbName + "_" + id+"`";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.execute();
            preparedStatement.close();
            con.close();

            con_backup = DriverManager.getConnection("jdbc:mysql://" + pIp + ":" + pPort + "/"+dbName+"_"+id+"?characterEncoding=UTF-8&nullCatalogMeansCurrent=true&serverTimezone=Asia/Shanghai",platUsername,platPassword); // generate the copied database
            metaData = con_sourceDB.getMetaData();
        } catch (Exception e) {
            e.printStackTrace();
            status=1;
        }
    }
    // sort according to foreign keys
    private void getTablesAndSort() {
        try {

            ResultSet tables = metaData.getTables(null,null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println(tableName);
                if (!tableInOrder.contains(tableName)) {
                    ResultSet tableForeignKey = metaData.getImportedKeys(null, null, tableName);
                    checkImportedKeys(tableForeignKey, tableInOrder, metaData, tableName);
                    tableInOrder.add(tableName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // sort-single table
    private void checkImportedKeys(ResultSet tableForeignKey, Set<String> set, DatabaseMetaData metaData, String tableName) {
        try {
            while (tableForeignKey.next()) {
                String referenceTable = tableForeignKey.getString("PKTABLE_NAME");
                if (!set.contains(referenceTable)) {
                    if (referenceTable.equals(tableName)) set.add(tableName);
                    else addTable(set, metaData, referenceTable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // add-recursive-checkImportedKeys
    private void addTable(Set<String> set, DatabaseMetaData metaData, String tableName) {
        try {
            ResultSet tableForeignKey = metaData.getImportedKeys(null, null, tableName);
            checkImportedKeys(tableForeignKey, set, metaData, tableName);
            set.add(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // create table...
    private void createTables(String tableName) {
        try {
            System.out.println("【table name: " + tableName + "】");

            String sql ="show create table "+tableName;
            System.out.println(sql);
            PreparedStatement ps = con_sourceDB.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            String sqlCreateTable="";
            while (resultSet.next()) {
                sqlCreateTable = resultSet.getString(2);
                System.out.println(sqlCreateTable);
            }
            resultSet.close();
            ps.close();

            PreparedStatement createTablePs=con_backup.prepareStatement(sqlCreateTable);
            createTablePs.execute();
            createTablePs.close();

        } catch (Exception e) {
            System.out.println("fail to create table." + e.toString());
        }
    }


}
