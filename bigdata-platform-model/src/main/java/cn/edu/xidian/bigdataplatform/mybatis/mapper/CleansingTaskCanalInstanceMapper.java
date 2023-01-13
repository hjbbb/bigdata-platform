package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskCanalInstance;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskSparkApp;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-04
 * @description:
 */
@Repository
public interface CleansingTaskCanalInstanceMapper {
    List<CleansingTaskCanalInstance> findAllCleansingTaskAndCanalInstance();

    CleansingTaskCanalInstance findCleansingTaskAndCanalInstance(Integer cleansingTaskId);


    Integer insertCleansingTaskAndCanalInstance(CleansingTaskCanalInstance cleansingTaskCanalInstance);

    int deleteCleansingTaskAndCanalInstanceById(Integer taskId);

    int updateCleansingTaskAndCanalInstance(CleansingTaskCanalInstance cleansingTaskCanalInstance);
}
