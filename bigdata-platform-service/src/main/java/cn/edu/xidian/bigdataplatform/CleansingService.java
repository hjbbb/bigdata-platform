package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.bean.CleansingTaskBean;
import cn.edu.xidian.bigdataplatform.bean.DatabaseInfoBean;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTask;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskCanalInstance;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskSparkApp;
import us.fatehi.utility.datasource.DatabaseConnectionSource;

import java.util.List;

public interface CleansingService {
    String submitTask(CleansingTaskBean task);

    DatabaseInfoBean fetchSchemaMetadata(String schemaName);

    List<CleansingTaskBean.SingleColumn> fetchSchema(String schemaName, String tableName);

    List<String> getTableNameListInSchema(String schemaName);

    List<CleansingTaskBean.Op> createOps(CleansingTaskBean.SingleColumn singleColumn);

    CleansingTaskBean.Op createDefaultOps(CleansingTaskBean.SingleColumn singleColumn);

    String convertTaskToConfigString(CleansingTaskBean taskBean, String sourceSchemaName, String sourceTableName);

    Integer insertNewCleansingTask(CleansingTask cleansingTaskModel);

    List<CleansingTaskBean> fetchAllCleansingTask();

    DatabaseConnectionSource getDataSourceForFetchingSchema(String schemaName, String host, String port);

    Integer insertCleansingTaskAndSparkApp(CleansingTaskSparkApp cleansingTaskSparkApp);

    boolean deleteCleansingTaskByTaskId(Integer taskId);

    boolean deleteCleansingTaskAndSparkAppByTaskId(Integer taskId);

    CleansingTask findCleansingTaskById(Integer taskId);

    boolean updateCleansingTaskById(CleansingTask cleansingTask);

    Integer insertCleansingTaskAndCanalInstance(CleansingTaskCanalInstance cleansingTaskCanalInstance);

    Integer updateCleansingTaskAndCanalInstance(CleansingTaskCanalInstance cleansingTaskCanalInstance);

    CleansingTaskCanalInstance findCleansingTaskAndCanalInstanceByTaskId(Integer cleansingTaskId);

    boolean deleteCleansingTaskAndCanalInstance(Integer taskId);
}
