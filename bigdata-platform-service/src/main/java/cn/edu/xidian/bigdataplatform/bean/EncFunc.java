package cn.edu.xidian.bigdataplatform.bean;

//import cn.hutool.crypto.SmUtil;
//import cn.hutool.crypto.asymmetric.SM2;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.*;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncFunc {
    MySQLSource mySQLSource;
    public String encScheme="RSA";
    public String tableName;
    public List<String> encFields;
    private int status=0;

    public EncFunc(MySQLSource mySQLSource, String tableName, List<String> encFields, String encScheme) {
        this.mySQLSource = mySQLSource;
        this.tableName = tableName;
        this.encFields = encFields;
        this.encScheme = encScheme;
    }

    private Connection getExportConnection(){
        try{
            return mySQLSource.createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            status=3;
            return null;
        }
    }
    private String encryptData(String data , String pkStr){
        byte[] pk= Base64.decode(pkStr);
        RSA rsa=new RSA(null,pk);
        String res=rsa.encryptBase64(data, KeyType.PublicKey);

//        String  sk="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC17/zA3XXwREea/YEAtkcXxfBsJcxKg0fgJteQcpuCCECMl3q6mav2GmTXZhCPR3IQwRgqPgiin/K3dwG+hyOp9UHIpBUeA0fl8wetOb8PruyfydVPhmpy707fbYpYyIFR7QspMsRXsiz1ClT3S5kmElEEoQZ5pNHs5/Dohk7FuNULg9nICXzgckTBE/MLpXY6Z4KTfGMuIUPeiNMs5eGmLheICftHJULoHHpqUXfh5dSwf3RqsZieqouUxo+pad0lNr2zFUi057r2W0LFklsd9Gldosy1pqJSGn8P/oJLuxNH6vMeZ7yyhAWYIhw0+uZKqgYa9AGYtyLvmsGgYoTrAgMBAAECggEAME9LNAMaH/Rhdxw9Nu0vr3ZVwsG+n0KAwVpO/wIPnNWIHkzSv92yNaUIhdGKMCFuflhnjD/wlw4VqvWktuy3YZfIIfi/yrpgSwTfUvX0b7upBTzYsUcl2OMGdHlntO94t0JUJlpenFKpcV+O9CNUxTEz5iDD0WUcgOWNqJCRNhrNYhRj8Wx/nBaPDK7kE/R5Eygs3sXWagVnszyjCRF/qJAdwG4uOh1UI0xldJhuGjHwtmSZIDrrpIvPj2aX+JskcBvk0VsenyW3PqSwODrq/nK9blcJLdhHn+0S2CTU74vmm3RxOsKEp9hAR1yfc43scCB9WDr+MvKxqIHBB3j3MQKBgQDjdLI+E0SamSc/RmE/na0nt3vQ6jPtkS5kDLWQKOHxBpSPTyzYrU/ocTaS673o3TA4Bnb7TvbjkzbrhQk8d1zoqaV2l5bvv6FOpnGHUE+MNiBBHdUxmU6zHwf19l7BdRUHzPV4i3c1krwB371uMP6bTOBbZXXizok90ILLjVNwZQKBgQDMxPSmPe1xBmLHDqHcQTYMUUwFWkZaXtd8kfIA8DxrV19NMZj7/rpIEQgU2TKS5JR/vNB9gmk9ZMjb40fhySyvlmzAZUFTqIIkz8gXH4nJB9Bzq83ZLZAyZWcryQrNv8aGPOwNwQwiWUms0EzOfqeWXpoC2K0hUid43FCEVzrDDwKBgAlxkejz4dhNXYzJ/kWm5UaN3q0o3CkT6woqYOtwVxkrvcVJc0I3x+aNtMda1FKzxEm/MvdyKtv+A7y5lE2XYxsh2AXZe2HO4gKi9Hm6wuK+aI5rj6t0gwj609jJdtuvYcUmJFbx62wlAHtPb80AacHG6lXBG6jkDFytqJCM+JblAoGBAJ2jvshAgovxo5ek/xzGIyrGZ2Tzg5fMzc27uXfZeloUsGyIE0NkbdpPKk6KizGkwWG5ZTRKcmRuM2d8iHkc28btYhmXmfwijNZ0E5EhmXxnBKYYDGf2eL0jbZK6uhN9YQM04XERPhsva9WbopWR0cikWaJ5VoeBbJvR2DaZxMYnAoGBAJWygPjm3VwXc1nFx1Xju3lJu2NL/9isezTmV5zwKnN0a/gvsA8rWgWRM+XqbiXIk//lkUiN9H9SCaJG0X8F2xkDWoAbY2qxlH8H6Xpr/2VXjx040Us0Z1nlEwMtFy9nSVTOiH4KmwfW+mAtkeumpzmpnCnIJOQQt19aCuJNa4hg";
//        byte[] sk1= Base64.decode(sk);
//        RSA rsa1=new RSA(sk1,null);
//        String ss= rsa1.decryptStr(res, KeyType.PrivateKey);
//        System.out.println(ss);
        System.out.println("data: " + data);
        System.out.println("pk: " + pkStr);
        System.out.println("res: " + res);
        return res;
    }

    private void insertSqldata(String pk){
        Connection connection=getExportConnection();
        String sql = "select * from `"+tableName+"`";
        try{
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                for(String encField : encFields) {
                    String rawDate=resultSet.getString(encField);
                    if(rawDate!=null){
                        String encRawData=encryptData(rawDate,pk);
                        resultSet.updateString(encField + "_enc", encRawData);
                    }
                    resultSet.updateRow();
                }
            }
            for(String encField : encFields) {
                String sqlDropColumn="update `"+tableName+"` set `"+encField+"`=null";
                PreparedStatement sqlDropColumnPS=connection.prepareStatement(sqlDropColumn);
                try {
                    sqlDropColumnPS.execute();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
                sqlDropColumnPS.close();
            }
            resultSet.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private boolean encSqlData(Connection encConnect, String pk) throws SQLException {
        String sql = "select * from `"+tableName+"`";
        try(PreparedStatement preparedStatement = encConnect.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            Vector<String> columnName= new Vector<>();

            int count = rsMetaData.getColumnCount();
            for(int i = 1; i<=count; i++) {
                columnName.add(rsMetaData.getColumnName(i));
            }
            boolean isContainField = false;
            for(String encField : encFields) {
                if(columnName.contains(encField)){
                    isContainField = true;
                    break;
                }
            }
            if(isContainField == false) {
                resultSet.close();
                preparedStatement.close();
                status=4;
                return false;
            }

            for(String encField : encFields) {
                String alterColumName=encField+"_enc";

                String sqlAddColumn="alter table `"+tableName+"` add column `"+alterColumName+"` longtext";

                //add a colum in mysql
                if(!columnName.contains(alterColumName)){
                    PreparedStatement sqlAddColumPS = encConnect.prepareStatement(sqlAddColumn);
                    sqlAddColumPS.execute();
                    sqlAddColumPS.close();
                }

                Thread tmp= new Thread(() -> insertSqldata(pk));
                tmp.start();
            }
        }
        return true;
    }

    public int execEncSql(String pk ){
        Connection encConnect = getExportConnection();
        if(encConnect==null){
            return status;
        }

        try{
            if(encSqlData(encConnect,pk))
                System.out.println("success");

        }catch (SQLException e){
            e.printStackTrace();
            status=2;
        }


        try {
            encConnect.close();
        }catch (SQLException e){
            e.printStackTrace();
            status=2;
        }

        return status;
    }

    public static KeyPair keyPairCreate(){
        return SecureUtil.generateKeyPair("RSA",2048);
    }

//    public static void main(String[] args) {
//        String url="jdbc:mysql://localhost:3306/platform?characterEncoding=UTF-8";
////        String url="http://127.0.0.1:9040/1/1.html";
//        System.out.println(getIP(url));
//
//    }

    public static Vector<String> getIP(String url){
        String re = "((http|ftp|https|jdbc:mysql)://)(([a-zA-Z0-9._-]+)|([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}))(([a-zA-Z]{2,6})|(:[0-9]{1,4})?)";
        String str = "";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            str = url;
        } else {
            String[] split2 = url.split(re);
            if (split2.length > 1) {
                String substring = url.substring(0, url.length() - split2[1].length());
                str = substring;
            } else {
                str = split2[0];
            }
        }
        String[] test=str.split(":");
        String ip= test[test.length-2].substring(2);
        String port=test[test.length-1];
//        System.out.println(ip+" "+port);
        Vector<String>res=new Vector<>();
        res.add(ip);
        res.add(port);
        return res;
    }

}

