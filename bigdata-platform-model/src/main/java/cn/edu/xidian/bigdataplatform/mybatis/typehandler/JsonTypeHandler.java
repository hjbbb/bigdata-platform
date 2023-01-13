package cn.edu.xidian.bigdataplatform.mybatis.typehandler;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@MappedTypes({DataSource.ConnectInfo.class})
public class JsonTypeHandler extends BaseTypeHandler<DataSource.ConnectInfo> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DataSource.ConnectInfo parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setObject(i, objectMapper.writeValueAsString(parameter.getOptions()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataSource.ConnectInfo getNullableResult(ResultSet resultSet, String s) throws SQLException {
        if(resultSet.getString(s) != null){
            try {
                TypeReference<HashMap<String,String>> typeRef
                        = new TypeReference<HashMap<String,String>>() {};
                HashMap<String, String> options =  objectMapper.readValue(resultSet.getString(s), typeRef);
                return new DataSource.ConnectInfo(options);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public DataSource.ConnectInfo getNullableResult(ResultSet resultSet, int i) throws SQLException {
        if(resultSet.getString(i) != null){
            try {
                TypeReference<HashMap<String,String>> typeRef
                        = new TypeReference<HashMap<String,String>>() {};
                HashMap<String, String> options =  objectMapper.readValue(resultSet.getString(i), typeRef);
                return new DataSource.ConnectInfo(options);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public DataSource.ConnectInfo getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        if(callableStatement.getString(i) != null){
            try {
                TypeReference<HashMap<String,String>> typeRef
                        = new TypeReference<HashMap<String,String>>() {};
                HashMap<String, String> options =  objectMapper.readValue(callableStatement.getString(i), typeRef);
                return new DataSource.ConnectInfo(options);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
