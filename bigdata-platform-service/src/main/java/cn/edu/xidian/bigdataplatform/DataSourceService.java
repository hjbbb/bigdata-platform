package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.bean.DataSourcePageResult;
import cn.edu.xidian.bigdataplatform.bean.PSpaceDTO;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.PSpace;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;
import org.springframework.lang.Nullable;

import java.util.List;

public interface DataSourceService {
    DataSourcePageResult getPagedDataSources(int pageNum, int pageSize);

    List<DataSource> getAllDataSources();

    List<MySQLSource> getMySQLSources();

    List<SpiderSource> getSpiderSources();

    SpiderSource addNewSpiderSource(SpiderSource spiderSource);

    @Nullable
    MySQLSource addNewMySQLDataSource(MySQLSource mySQLSource);

    @Nullable
    MySQLSource modifyMySQLDataSource(MySQLSource mySQLSource);

    @Nullable
    SpiderSource modifySpiderDataSource(SpiderSource spiderSource);

    boolean deleteDataSource(String sourceId, String sourceType);

    MySQLSource findMySQLSourceById(String sourceId);

    SpiderSource findSpiderSourceById(String sourceId);

    boolean checkMySQLConnectionSuccess(MySQLSource mySQLSource);

    List<PSpaceDTO> getPSpaceSources();

    PSpace findPSpaceByDataType(String dataType);

    boolean updatePSpaceSource(String url, String user, String password);
}
