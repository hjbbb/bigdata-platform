package cn.edu.xidian.bigdataplatform.bean;

import com.alibaba.fastjson.JSON;

import java.sql.*;
import java.util.Vector;


public class UserApiControl {
    public String id,userName,apiCatalogue;
    public String apiName,apiUrl,apiProtocol,apiRequestMethod,description,enableJournal,lifeTime;
    String paramName,paramPosition,paramType;
    public String indexName;
    String securityCertification;
    String dataSourceType,database,table,requestParam,returnParam;
    public int status;

    public String showApi(propertiesRead thisSqlProperties){
        Vector<String> ipAndPort=EncFunc.getIP(thisSqlProperties.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);
        Vector<Vector<String>> sqlData=ShowFunc.showSqlData(ip,port,"platform","user_API_control",
                null,thisSqlProperties.username,thisSqlProperties.password);

        if(sqlData==null){
            status=2;
        }
        return JSON.toJSONString(sqlData);
    }

    public int delectApi(propertiesRead thisSqlProperties){
        if (id==null)
            return 4;
        Vector<String> ipAndPort=EncFunc.getIP(thisSqlProperties.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);
        dataDelete(ip,port,thisSqlProperties.username,thisSqlProperties.password,id);
        return status;
    }

    public int registerController(propertiesRead thisSqlProperties) {
        if(userName==null||indexName==null||apiName==null){
            status=4;
            return status;
        }
        Vector<String> ipAndPort=EncFunc.getIP(thisSqlProperties.url);
        String ip=ipAndPort.get(0);
        String port=ipAndPort.get(1);
        if(!sqlCheck(ip,port,thisSqlProperties.username,thisSqlProperties.password))
            return status;

        String url="/userApi/"+userName+"_"+indexName;
        if(!apiCheck(ip,port,thisSqlProperties.username,thisSqlProperties.password,url)) {
            status = 3;
            return status;
        }

        dataInsert(ip, port, thisSqlProperties.username, thisSqlProperties.password, url);

        return status;
    }
    public boolean apiCheck(String ip,String port,String SqluserName,String passWord,String apiUrl){
        try{
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/"+"platform"+"?characterEncoding=UTF-8";
            Connection sqlCon=DriverManager.getConnection(url, SqluserName, passWord);
            String sql="select id from user_API_control where api_url=" +
                    "\""+apiUrl+"\"" ;
            PreparedStatement preparedStatement = sqlCon.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            Vector<String>id =new Vector<>();
            while (resultSet.next()){
                id.add(resultSet.getString(1));
            }
            resultSet.close();
            preparedStatement.close();
            sqlCon.close();
            return id.size() == 0;

        }catch (SQLException e){
            e.printStackTrace();
            status=2;
            return false;
        }
    }
    private void dataDelete(String ip, String port, String SqluserName, String passWord, String id){
        try{
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/"+"platform"+"?characterEncoding=UTF-8";
            Connection sqlCon=DriverManager.getConnection(url, SqluserName, passWord);
            String sql="delete from user_API_control where id="+id;
            sqlCon.prepareStatement(sql).execute();
            sqlCon.close();
        }catch (SQLException e){
            e.printStackTrace();
            status=2;
        }
    }

    private void dataInsert(String ip, String port, String SqluserName, String passWord, String apiUrl){
        try{
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/"+"platform"+"?characterEncoding=UTF-8";
            Connection sqlCon=DriverManager.getConnection(url, SqluserName, passWord);
            String sql="insert into user_API_control (api_name,user,index_name,api_url) values(" +
                    "\""+apiName+"\"," +
                    "\""+userName+"\"," +
//                    "\""+lifeTime+"\"," +
                    "\""+indexName+"\"," +
                    "\""+apiUrl+"\"" +
                    ")";
            sqlCon.prepareStatement(sql).execute();
            sqlCon.close();
        }catch (SQLException e){
            e.printStackTrace();
            status=2;
        }
    }
    private boolean sqlCheck(String ip,String port,String userName,String passWord){
        Vector<String>tables=ShowFunc.showTables(ip,port,"platform",userName,passWord);
        if(tables==null)
            return false;
        else if(tables.contains("user_API_control"))
            return true;
        try{
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://" + ip + ":" + port + "/"+"platform"+"?characterEncoding=UTF-8";
            Connection sqlCon=DriverManager.getConnection(url, userName, passWord);
            String sql="create table user_API_control(" +
                    "id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                    "api_name varchar(50)," +
                    "api_url varchar(50)," +
                    "user varchar(30)," +
//                    "life_time varchar(20)" +
                    "index_name varchar(30)" +
                    ")ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";
            sqlCon.prepareStatement(sql).execute();
            sqlCon.close();
        }catch (SQLException e){
            e.printStackTrace();
            status=1;
            return false;
        }
        return true;
    }



//    ApplicationContext applicationContext;

//    public void registerController(ServletContext servletContext) throws NoSuchMethodException {
//
//
//        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//
//        assert context != null;
//        RequestMappingHandlerMapping  bean = context.getBean(RequestMappingHandlerMapping.class);
//
//        RequestMappingInfo info = RequestMappingInfo.paths("/api/users111/").methods(RequestMethod.GET).build();
//
//        Method method = RegisterApiBean.class.getMethod("getUsers");
//
//        bean.registerMapping(info,"testBean",method);
//
//
//    }
}
