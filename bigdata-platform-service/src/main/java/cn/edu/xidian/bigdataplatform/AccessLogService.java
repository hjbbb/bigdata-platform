package cn.edu.xidian.bigdataplatform;


import cn.edu.xidian.bigdataplatform.bean.AccessLogPageResult;
import cn.edu.xidian.bigdataplatform.bean.MaskingTaskPageResult;
import cn.edu.xidian.bigdataplatform.mybatis.entity.AccessLog;

import java.util.List;

public interface AccessLogService {

    List<AccessLog> searchAllAccessLog();

    AccessLogPageResult getPagedAccessLog(int pageNum, int pageSize);

    AccessLogPageResult getPagedAccessLogByName(int pageNum, int pageSize,String username);

    AccessLog addAccesslog(String username,String operate);

    AccessLog addNewAccessLog(AccessLog accessLog);
}
