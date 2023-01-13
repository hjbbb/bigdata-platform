package cn.edu.xidian.bigdataplatform.bean;

import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.EncKeyPair;
import cn.hutool.core.codec.Base64;

import java.security.KeyPair;

public class keyCreate {
    private String username="null";
    private String encSchema="RSA";

    private int status=0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncSchema() {
        return encSchema;
    }

    public void setEncSchema(String encSchema) {
        this.encSchema = encSchema;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    private void insertPkIntoSql(String pk)  {
//        try{
//            Driver driver = new com.mysql.cj.jdbc.Driver();
//            DriverManager.registerDriver(driver);
//            Connection connection=DriverManager.getConnection(sqlInfo.url, sqlInfo.username, sqlInfo.password);
//
//            //get ColumnName
//            String sql = "select * from `user_info`";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            ResultSetMetaData rsMetaData = resultSet.getMetaData();
//            Vector<String> columnName= new Vector<String>();
//            int count = rsMetaData.getColumnCount();
//            for(int i = 1; i<=count; i++) {
//                columnName.add(rsMetaData.getColumnName(i));
//            }
//            if(!columnName.contains("user_publickey")){
//                String addPkColumn="alter table user_info add user_publickey longtext";
//                connection.prepareStatement(addPkColumn).execute();
//            }
//            String insertPkSql="update user_info set user_publickey= \""+pk+"\"where username ='"+username+"'";
//            connection.prepareStatement(insertPkSql).execute();
////            System.out.println(insertPkSql);
//            resultSet.close();
//            preparedStatement.close();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            status=1;
//        }
//    }


    public EncKeyPair create(){
        KeyPair keyPair = EncFunc.keyPairCreate();
        String pk = Base64.encode(keyPair.getPublic().getEncoded());
        String sk = Base64.encode(keyPair.getPrivate().getEncoded());
        EncKeyPair encKeyPair = new EncKeyPair(pk, sk);
        return encKeyPair;
    }
}
