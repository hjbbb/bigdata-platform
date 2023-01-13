package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CleansingTaskMapper {
    CleansingTask findCleansingTaskByAppId(String appId);

    CleansingTask findCleansingTaskByIncrementalId(Integer id);

    Integer insertCleansingTask(CleansingTask cleansingTask);

    boolean updateCleansingTaskStatusById(CleansingTask cleansingTask);

    boolean updateCleansingTaskStatusByAppId(CleansingTask cleansingTask);

    List<CleansingTask> findAllCleansingTask();

    int deleteCleansingTaskById(Integer taskId);
}
