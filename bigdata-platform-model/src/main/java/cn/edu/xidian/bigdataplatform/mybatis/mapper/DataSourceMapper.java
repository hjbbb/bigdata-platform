package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataSourceMapper {
    List<DataSource> queryAllDataSources();

    int insertNewDataSource(DataSource newSource);

    int updateExistedDataSourceById(DataSource dataSource);

    int deleteExistedSourceById(String sourceId);

    DataSource findDataSourceById(String sourceId);
}
