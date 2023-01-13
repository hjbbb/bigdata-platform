package cn.edu.xidian.bigdataplatform.bean;


import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;

import java.sql.*;
import java.util.*;

public class EncTask {
    public String dataSourceId;
    public String tableName;
    public List<String> encFields;
    public String userName;
    int status =0;


    public String getUserPk(propertiesRead platformSqlInfo){
        try{
            //注册驱动（new com.mysql.jdbc.Driver()也是可以的）
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            Connection connection=DriverManager.getConnection(platformSqlInfo.url, platformSqlInfo.username, platformSqlInfo.password);
            String sql = "select * from user_info where username ='"+userName+"'";
//            System.out.println(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            Vector<String> columnName= new Vector<String>();
            int count = rsMetaData.getColumnCount();
            for(int i = 1; i<=count; i++) {
                columnName.add(rsMetaData.getColumnName(i));
            }
            if(!columnName.contains("user_publickey")){
                preparedStatement.close();
                connection.close();
                status=1;
            }
            String pk=null;
            if(resultSet.next()){
                pk=resultSet.getString("user_publickey");
            }
            else {
                status=1;
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
            return pk;

        } catch (SQLException e) {
            e.printStackTrace();
            status=2;
            return null;
        }
    }

    public int  exec(String pk, MySQLSource mySQLSource){
        if(pk==null){
            return status;
        }

        EncFunc encFunc=new EncFunc(mySQLSource,tableName, encFields,"RSA");
//        System.out.println(pk);
        status= encFunc.execEncSql(pk);

        return status;
    }

}
