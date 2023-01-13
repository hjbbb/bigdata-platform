package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskSparkApp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CleansingTaskSparkAppMapper {
    List<CleansingTaskSparkApp> findAllCleansingTaskAndSparkApp();

    Integer insertCleansingTaskAndSparkApp(CleansingTaskSparkApp cleansingTaskSparkApp);

    int deleteCleansingTaskAndSparkAppById(Integer taskId);
}
