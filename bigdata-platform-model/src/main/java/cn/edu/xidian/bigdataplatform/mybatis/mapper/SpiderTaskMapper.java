package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.SpiderTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-19
 * @description:
 */
@Mapper
public interface SpiderTaskMapper {
    SpiderTask findSpiderTaskById(Integer id);

    Integer insertSpiderTask(SpiderTask spiderTask);

    boolean updateSpiderTaskStatus(SpiderTask spiderTask);

    List<SpiderTask> findAllSpiderTask();
}
