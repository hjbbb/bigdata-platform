package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.AccessLog;
import cn.edu.xidian.bigdataplatform.mybatis.entity.Maskpojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AccessLogMapper {

    List<AccessLog> searchAllAccessLog();
    List<AccessLog> searchAllAccessLogByName(AccessLog newAccessLog);

    int insertAccessLog(AccessLog newAccessLog);
}
