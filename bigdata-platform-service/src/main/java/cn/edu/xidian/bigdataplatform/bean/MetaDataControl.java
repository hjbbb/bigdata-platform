package cn.edu.xidian.bigdataplatform.bean;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import com.alibaba.fastjson.JSON;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MetaDataControl {
    public String id;
    public String dataType;
    public String databaseName;
    public String tableName;
    public String tableExplain;
    int status = 0;

    public int getStatus() {
        return status;
    }

    public static void main(String[] args) {
        MetaDataControl test = new MetaDataControl();
        propertiesRead info = new propertiesRead();
        test.dataType = "text";
        test.databaseName = "mysql";
        test.tableName = "333";
        test.tableExplain = "explain";
        test.id = "2";

        info.url = "jdbc:mysql://localhost:3306/platform?characterEncoding=UTF-8";
        info.username = "root";
        info.password = "123";

        System.out.println(test.show(info));
    }

    private boolean check(String ip, String port, String username, String password) {
        Vector<String> databases = ShowFunc.showDatabases(ip, port, username, password);
        if (databases == null) {
            status = 1;//平台数据库无法链接
            return false;
        } else {
            if (!databases.contains("platform")) {
                try {
                    Driver driver = new com.mysql.cj.jdbc.Driver();
                    DriverManager.registerDriver(driver);
                    String url = "jdbc:mysql://" + ip + ":" + port + "/" + "?characterEncoding=UTF-8";
                    Connection sqlCon = DriverManager.getConnection(url, username, password);
                    String sql = "create database platform";
                    sqlCon.prepareStatement(sql).execute();
                    sqlCon.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    status = 2;//数据库错误
                }
            }
        }
        Vector<String> tables = ShowFunc.showTables(ip, port, "platform", username, password);
        if (tables == null) {
            status = 1;//平台数据库无法链接
        } else {
            if (!tables.contains("table_matadata")) {
                try {
                    Driver driver = new com.mysql.cj.jdbc.Driver();
                    DriverManager.registerDriver(driver);
                    String url = "jdbc:mysql://" + ip + ":" + port + "/" + "platform" + "?characterEncoding=UTF-8";
                    Connection sqlCon = DriverManager.getConnection(url, username, password);
                    String sql = "create table table_matadata(" +
                            "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                            "dataType varchar(100)," +
                            "databaseName varchar(100)," +
                            "tableName varchar(100)," +
                            "tableExplain text" +
                            ")ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";
                    sqlCon.prepareStatement(sql).execute();
                    sqlCon.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    status = 2;
                }
            }
        }
        return status <= 0;
    }

    public int add(propertiesRead platformSqlInfo) {
        Vector<String> ipAndPort = EncFunc.getIP(platformSqlInfo.url);
        String ip = ipAndPort.get(0);
        String port = ipAndPort.get(1);
        if (dataType == null || databaseName == null || tableName == null || tableExplain == null) {
            status = 4;//参数不全
            return status;
        }
        if (!check(ip, port, platformSqlInfo.username, platformSqlInfo.password)) {
            return status;
        }
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + "platform" + "?characterEncoding=UTF-8";
            Connection sqlCon = DriverManager.getConnection(url, platformSqlInfo.username, platformSqlInfo.password);
            String sql = "insert into table_matadata (dataType,databaseName,tableName,tableExplain) " +
                    "values(\"" + dataType + "\",\"" + databaseName + "\",\"" + tableName + "\",\"" + tableExplain + "\")";
            sqlCon.prepareStatement(sql).execute();
            sqlCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
            status = 2;
        }
        return status;
    }

    public int delect(propertiesRead platformSqlInfo) {
        Vector<String> ipAndPort = EncFunc.getIP(platformSqlInfo.url);
        String ip = ipAndPort.get(0);
        String port = ipAndPort.get(1);
        if (!check(ip, port, platformSqlInfo.username, platformSqlInfo.password)) {
            return status;
        }
        if (id == null) {
            status = 4;//参数不全
            return status;
        }
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + "platform" + "?characterEncoding=UTF-8";
            Connection sqlCon = DriverManager.getConnection(url, platformSqlInfo.username, platformSqlInfo.password);
            String sql = "delete from table_matadata where id =" + id + "";
            sqlCon.prepareStatement(sql).execute();
            sqlCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
            status = 3;//删除的列不存在
        }

        return status;
    }

    public int updata(propertiesRead platformSqlInfo) {
        Vector<String> ipAndPort = EncFunc.getIP(platformSqlInfo.url);
        String ip = ipAndPort.get(0);
        String port = ipAndPort.get(1);
        if (dataType == null || databaseName == null || tableName == null || tableExplain == null || id == null) {
            status = 4;//参数不全
            return status;
        }
        if (!check(ip, port, platformSqlInfo.username, platformSqlInfo.password)) {
            return status;
        }
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + "platform" + "?characterEncoding=UTF-8";
            Connection sqlCon = DriverManager.getConnection(url, platformSqlInfo.username, platformSqlInfo.password);
            String sql = "update table_matadata set dataType" + "=\"" + dataType + "\"," +
                    "databaseName" + "=\"" + databaseName + "\"," +
                    "tableName" + "=\"" + tableName + "\"," +
                    "tableExplain" + "=\"" + tableExplain + "\" " +
                    "where id =" + id;
            sqlCon.prepareStatement(sql).execute();
            sqlCon.close();

        } catch (SQLException e) {
            e.printStackTrace();
            status = 3;//
        }
        return status;
    }

    public String show(propertiesRead platformSqlInfo) {
        Vector<String> ipAndPort = EncFunc.getIP(platformSqlInfo.url);
        String ip = ipAndPort.get(0);
        String port = ipAndPort.get(1);
        if (!check(ip, port, platformSqlInfo.username, platformSqlInfo.password)) {
            return null;
        }
        if (id == null) {
            status = 4;//参数不全
        }
        Vector<Vector<String>> sqlData = ShowFunc.showSqlData(ip, port, "platform", "table_matadata", null, platformSqlInfo.username, platformSqlInfo.password);

        if (sqlData == null) {
            status = 2;
        }
        return JSON.toJSONString(sqlData);

    }

    public static List<MetaDataItem> fetchMetaData(MySQLSource mySQLSource, String tableName) throws SQLException {
        List<MetaDataItem> metaDataItemList = new ArrayList<>();
        String sql = "show columns from " + tableName;
        try (Connection conn = mySQLSource.createConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                MetaDataItem metaDataItem = new MetaDataItem();
                metaDataItem.setField(rs.getString("Field"));
                metaDataItem.setType(rs.getString("Type"));
                metaDataItem.setAllowNull(rs.getString("Null"));
                metaDataItem.setKey(rs.getString("Key"));
                metaDataItem.setDefault(rs.getString("Default"));
                metaDataItem.setExtra(rs.getString("Extra"));
                metaDataItemList.add(metaDataItem);
            }
        }
        return metaDataItemList;
    }

    private static MetaDataItem fetchSingleColumnMetaData(MySQLSource mySQLSource, String tableName, String columnName) throws SQLException {
        String sql = "show columns from " + tableName + " where field = '" + columnName + "'";
        try (Connection conn = mySQLSource.createConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            MetaDataItem metaDataItem = new MetaDataItem();
            metaDataItem.setField(rs.getString("Field"));
            metaDataItem.setType(rs.getString("Type"));
            metaDataItem.setAllowNull(rs.getString("Null"));
            metaDataItem.setKey(rs.getString("Key"));
            metaDataItem.setDefault(rs.getString("Default"));
            metaDataItem.setExtra(rs.getString("Extra"));
            return metaDataItem;
        }
    }

    public static void alterColumn(MySQLSource mySQLSource, String tableName, String columnName, MetaDataItem metaDataItem) throws SQLException {
        StringBuffer buf = new StringBuffer("alter table " + tableName + " change column " + columnName);
        try (Connection conn = mySQLSource.createConnection();
             Statement stmt = conn.createStatement()) {
            MetaDataItem initMetaDataItem = fetchSingleColumnMetaData(mySQLSource, tableName, columnName);
            if(initMetaDataItem.getKey()!=metaDataItem.getKey() && !initMetaDataItem.getKey().equals("")) {
                if(initMetaDataItem.getKey().equals("PRI")) {
                    stmt.executeUpdate("alter table " + tableName + " drop primary key");
                } else {
                    stmt.executeUpdate("alter table " + tableName + " drop index " + columnName);
                }
            }
            String newColumnName = metaDataItem.getField();
            String type = metaDataItem.getType();
            String allowNull = metaDataItem.getAllowNull();
            String key = metaDataItem.getKey();
            String defaultVal = metaDataItem.getDefault();
            buf.append(" ").append(newColumnName);
            buf.append(" ").append(type);
            buf.append(" ").append(allowNull.equals("NO") ? "NOT NULL" : "NULL");
            if (defaultVal != null && !defaultVal.equals("")) {
                buf.append(" default ").append(defaultVal);
            }
            buf.append(" ").append(key.equals("PRI") ? "PRIMARY KEY" : key.equals("UNI") ? "UNIQUE KEY" : "");
            String sql = buf.toString();
            System.out.println(sql);
            stmt.executeUpdate(sql);
        }
    }
}
