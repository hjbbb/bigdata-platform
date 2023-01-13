package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MySQLSourceMapper {
    List<MySQLSource> queryAllMySQLSources();

    int insertNewMySQLSource(MySQLSource newSource);

    int updateExistedMySQLSourceById(MySQLSource dataSource);

    int deleteExistedSourceById(Integer sourceId);

    MySQLSource findMySQLSourceById(Integer sourceId);
}
