package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.MySQLTask;
import cn.edu.xidian.bigdataplatform.mybatis.entity.SpiderTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MySQLTaskMapper {
    MySQLTask findMySQLTaskById(Integer id);

    Integer insertMySQLTask(MySQLTask mySQLTask);

    boolean updateMySQLTaskStatus(MySQLTask mySQLTask);

    List<MySQLTask> findAllMySQLTask();
}
