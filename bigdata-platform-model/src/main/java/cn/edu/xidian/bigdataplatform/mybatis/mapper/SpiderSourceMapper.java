package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpiderSourceMapper {
    List<SpiderSource> queryAllSpiderSources();

    int insertNewSpiderSource(SpiderSource newSource);

    int updateExistedSpiderSourceById(SpiderSource dataSource);

    int deleteExistedSourceById(Integer sourceId);

    SpiderSource findSpiderSourceById(Integer sourceId);

    boolean updateSpiderSourceStatus(SpiderSource spiderSource);

    List<SpiderSource> findAllSpiderSource();

    String findSpiderSourceByName(String name);

}
