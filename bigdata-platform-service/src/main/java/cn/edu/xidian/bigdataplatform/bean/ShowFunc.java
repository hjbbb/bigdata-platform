package cn.edu.xidian.bigdataplatform.bean;

import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.TableData;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import com.alibaba.fastjson.JSON;
import net.sf.jsqlparser.statement.show.ShowTablesStatement;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class ShowFunc {

    public static Vector<String> showDatabases(String ip, String port, String userName, String passWord) {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + "?characterEncoding=UTF-8";
            Connection sqlCon = DriverManager.getConnection(url, userName, passWord);

            Vector<String> conShowName = new Vector<>();
            conShowName.add("information_schema");
            conShowName.add("mysql");
            conShowName.add("performance_schema");
            conShowName.add("sys");

            String sql = "show databases";
            PreparedStatement preparedStatement = sqlCon.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            Vector<String> res = new Vector<String>();
            while (resultSet.next()) {
                String databaseName = resultSet.getString("Database");
                if (!conShowName.contains(databaseName))
                    res.add(databaseName);
            }
            resultSet.close();
            preparedStatement.close();
            sqlCon.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Vector<String> showTables(String ip, String port, String dataBase, String userName, String passWord) {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + dataBase + "?characterEncoding=UTF-8";
            Connection sqlCon = DriverManager.getConnection(url, userName, passWord);

            String sql = "show tables";
            PreparedStatement preparedStatement = sqlCon.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            Vector<String> res = new Vector<String>();

            while (resultSet.next()) {
                String columnName = resultSet.getString(1);
                res.add(columnName);
            }
            resultSet.close();
            preparedStatement.close();
            sqlCon.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Vector<String> showTables(MySQLSource mySQLSource) throws SQLException {
        String sql = "show tables";
        try( Connection sqlCon = mySQLSource.createConnection();PreparedStatement preparedStatement = sqlCon.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            Vector<String> res = new Vector<String>();

            while (resultSet.next()) {
                String columnName = resultSet.getString(1);
                res.add(columnName);
            }
            resultSet.close();
            return res;
        }
    }

    public static Vector<String> showColumnName(String ip, String port, String dataBase, String tableName, String userName, String passWord) {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + dataBase + "?characterEncoding=UTF-8";
            Connection sqlCon = DriverManager.getConnection(url, userName, passWord);

            String sql = "select * from `" + tableName + "`";
            PreparedStatement preparedStatement = sqlCon.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            Vector<String> columnName = new Vector<String>();

            int count = rsMetaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                columnName.add(rsMetaData.getColumnName(i));
            }
            resultSet.close();
            preparedStatement.close();
            sqlCon.close();
            return columnName;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Vector<String> showColumnName(MySQLSource mySQLSource, String tableName) {
        try {
            Connection sqlCon = mySQLSource.createConnection();
            String sql = "select * from `" + tableName + "`";
            PreparedStatement preparedStatement = sqlCon.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            Vector<String> columnName = new Vector<String>();

            int count = rsMetaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                columnName.add(rsMetaData.getColumnName(i));
            }
            resultSet.close();
            preparedStatement.close();
            sqlCon.close();
            return columnName;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Vector<Vector<String>> showSqlData(String ip, String port, String dataBase, String tableName, String[] field, String userName, String passWord) {
        if (field == null) {
            field = new String[]{"*"};
        }
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + dataBase + "?characterEncoding=UTF-8";
            Connection sqlCon = DriverManager.getConnection(url, userName, passWord);

            String willShowColumns = "";
            if (field[0].equals("*")) {
                willShowColumns += "*";
            } else {
                Vector<String> existColumns = showColumnName(ip, port, dataBase, tableName, userName, passWord);
                if (existColumns != null) {
                    for (String s : field) {
                        if (existColumns.contains(s)) {
                            willShowColumns = willShowColumns + s + ",";
                        }
                    }
                    willShowColumns = willShowColumns.substring(0, willShowColumns.length() - 1);
                }
            }


            String sql = "select " + willShowColumns + " from `" + tableName + "`";
            System.out.println(sql);
            PreparedStatement preparedStatement = sqlCon.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int count = rsMetaData.getColumnCount();

            Vector<Vector<String>> res = new Vector<>();
            while (resultSet.next()) {
                Vector<String> listData = new Vector<>();
                for (int i = 1; i <= count; i++) {
                    listData.add(resultSet.getString(i));
                }
                res.add(listData);
            }
            resultSet.close();
            preparedStatement.close();
            sqlCon.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TableData showSqlData(MySQLSource mySQLSource, String tableName, List<String> fields, int pageNum, int pageSize) {
        TableData tableData = new TableData();
        if (fields == null) {
            fields = new ArrayList<>(Arrays.asList("*"));
        }
        try {
            Connection sqlCon = mySQLSource.createConnection();

            String willShowColumns = "";
            if (fields.get(0).equals("*")) {
                willShowColumns += "*";
                tableData.setColumns(showColumnName(mySQLSource, tableName));
            } else {
                Vector<String> existColumns = showColumnName(mySQLSource, tableName);
                if (existColumns != null) {
                    fields.retainAll(existColumns);
                    willShowColumns = String.join(",", fields);
                }
            }
            String sql = "select count(*) as total_count from `" + tableName + "`";
            PreparedStatement preparedStatement = sqlCon.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            tableData.setTotalCount(resultSet.getLong(1));
            sql = "select " + willShowColumns + " from `" + tableName + "` limit ?, ?";
            System.out.println(sql);
            preparedStatement = sqlCon.prepareStatement(sql);
            preparedStatement.setInt(1, (pageNum - 1) * pageSize);
            preparedStatement.setInt(2, pageSize);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int count = rsMetaData.getColumnCount();

            List<List<String>> res = new Vector<>();
            while (resultSet.next()) {
                Vector<String> listData = new Vector<>();
                for (int i = 1; i <= count; i++) {
                    listData.add(resultSet.getString(i));
                }
                res.add(listData);
            }
            tableData.setData(res);
            resultSet.close();
            preparedStatement.close();
            sqlCon.close();
            return tableData;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//
//        Vector<Vector<String>> sqlData=showSqlData("127.0.0.1","3306","test_export","test_export_table",null,"root","123");
//        System.out.println(JSON.toJSONString(sqlData));
//    }

}
