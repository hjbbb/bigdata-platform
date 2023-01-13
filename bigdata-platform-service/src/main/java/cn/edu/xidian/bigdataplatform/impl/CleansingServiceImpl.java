package cn.edu.xidian.bigdataplatform.impl;

import cn.edu.xidian.bigdataplatform.CleansingService;
import cn.edu.xidian.bigdataplatform.DataSourceService;
import cn.edu.xidian.bigdataplatform.bean.CleansingTaskBean;
import cn.edu.xidian.bigdataplatform.bean.CleansingTaskBean.Op;
import cn.edu.xidian.bigdataplatform.bean.DatabaseInfoBean;
import cn.edu.xidian.bigdataplatform.bean.PSpaceTableRelation;
import cn.edu.xidian.bigdataplatform.bean.SpiderTableRelation;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTask;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskCanalInstance;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskSparkApp;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.*;
import cn.edu.xidian.bigdataplatform.spark.SparkHTTPClient;
import cn.edu.xidian.bigdataplatform.spark.yarn.App;
import cn.edu.xidian.bigdataplatform.spark.yarn.JsonRootBean;
import cn.edu.xidian.bigdataplatform.template.ConfigTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import schemacrawler.schema.*;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.tools.utility.SchemaCrawlerUtility;
import us.fatehi.utility.datasource.DatabaseConnectionSource;
import us.fatehi.utility.datasource.DatabaseConnectionSources;
import us.fatehi.utility.datasource.MultiUseUserCredentials;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static cn.edu.xidian.bigdataplatform.bean.CleansingTaskBean.Op.OP_TRIM;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-03
 * @description:
 */
@Service
public class CleansingServiceImpl implements CleansingService {


    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private CleansingTaskMapper cleansingTaskMapper;

    @Autowired
    private CleansingTaskSparkAppMapper cleansingTaskSparkAppMapper;

    @Autowired
    private MySQLTaskMapper mySQLTaskMapper;

    @Autowired
    private SpiderTaskMapper spiderTaskMapper;

    @Autowired
    private MySQLSourceMapper mySQLSourceMapper;

    @Autowired
    private SpiderSourceMapper spiderSourceMapper;

    @Autowired
    private CleansingTaskCanalInstanceMapper cleansingTaskCanalInstanceMapper;

    @Autowired
    private SparkHTTPClient sparkClient;

    @Value("${us.fatehi.datasource.address}")
    private String dataSourceAddress;

    @Value("${us.fatehi.datasource.user}")
    private String dataSourceUser;

    @Value("${us.fatehi.datasource.password}")
    private String dataSourcePassword;

    @Value("${kafka.brokers}")
    private String kafkaBrokers;

    @Value("${es.hostName}")
    private String esHostName;

    private static Logger logger = LoggerFactory.getLogger(CleansingService.class);

    @Override
    public String submitTask(CleansingTaskBean task) {
//        DataSource chosenDataSource = dataSourceMapper.findDataSourceById(task.inputTableId);
//        HashMap<String, String> connectParams = chosenDataSource.getConnectInfo().getOptions();
        // convertRulesToConfig
        //

        return null;
    }

    @Override
    public DatabaseInfoBean fetchSchemaMetadata(String schemaName) {
        final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions();
        final Catalog catalog = SchemaCrawlerUtility.getCatalog(getDataSourceForFetchingSchema(schemaName), options);
        List<Schema> schemas = new ArrayList<>(catalog.getSchemas());
        List<Table> tables = new ArrayList<>(catalog.getTables());
        DatabaseInfoBean dbInfoBean = new DatabaseInfoBean();
        dbInfoBean.setSchemas(schemas);
        dbInfoBean.setTables(tables);
        return dbInfoBean;
    }

    public List<CleansingTaskBean.SingleColumn> fetchSchema(String schemaName, String tableName) {
        final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions();
        final Catalog catalog = SchemaCrawlerUtility.getCatalog(getDataSourceForFetchingSchema(schemaName), options);
        Collection<Schema> schemas = catalog.getSchemas();
        if (schemas == null || schemas.isEmpty()) {
            return null;
        }
        Collection<Table> tables = catalog.getTables();
        ArrayList<CleansingTaskBean.SingleColumn> columnResult = new ArrayList<>();
        for (Table table : tables) {
            if (table.getName().equals(tableName) && table.getSchema().getFullName().equals(schemaName)) {
                List<Column> columns = table.getColumns();
                for (Column column : columns) {
                    CleansingTaskBean.SingleColumn singleColumn = new CleansingTaskBean.SingleColumn();
                    singleColumn.columnName = column.getName();
                    singleColumn.databaseSpecificType = column.getColumnDataType().getDatabaseSpecificTypeName();
                    singleColumn.availableOps = createOps(singleColumn);
                    columnResult.add(singleColumn);
                }
            }
        }
        return columnResult;
    }

    @Override
    public List<String> getTableNameListInSchema(String schemaName) {
        final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions();
        final Catalog catalog = SchemaCrawlerUtility.getCatalog(getDataSourceForFetchingSchema(schemaName), options);
        List<Schema> schemas = (List<Schema>) catalog.getSchemas();
        if (schemas == null || schemas.isEmpty()) {
            return null;
        }
        List<Table> tables = new ArrayList<>();
        for (Schema schema : schemas) {
            if (schema.getFullName().equals(schemaName)) {
                tables = (List<Table>) catalog.getTables(schema);
            }
        }
        List<String> tableNameList = new ArrayList<>();
        for (Table table : tables) {
            tableNameList.add(table.getName());
        }

        return tableNameList;
    }

    public List<Op> createOps(CleansingTaskBean.SingleColumn singleColumn) {
        switch (singleColumn.databaseSpecificType) {
            case "TINYINT":
            case "SMALLINT":
            case "MEDIUMINT":
            case "INT":
            case "BIGINT":
            case "FLOAT":
            case "DOUBLE":
            case "DECIMAL":
                return new ArrayList<Op>(Op.numericOps);
            case "CHAR":
            case "TEXT":
            case "TINYTEXT":
            case "MEDIUMTEXT":
            case "LONGTEXT":
            case "VARCHAR":
                return new ArrayList<Op>(Op.stringOps);
            default:
                return new ArrayList<>();
        }
    }

    public Op createDefaultOps(CleansingTaskBean.SingleColumn singleColumn) {
        switch (singleColumn.databaseSpecificType) {
            case "TINYINT":
            case "SMALLINT":
            case "MEDIUMINT":
            case "INT":
            case "BIGINT":
            case "FLOAT":
            case "DOUBLE":
            case "DECIMAL":
                return Op.OP_NONE;
            case "CHAR":
            case "TEXT":
            case "TINYTEXT":
            case "MEDIUMTEXT":
            case "LONGTEXT":
            case "VARCHAR":
                return OP_TRIM;
            default:
                return Op.OP_NONE;
        }
    }

    @Override
    public String convertTaskToConfigString(CleansingTaskBean taskBean, String sourceSchemaName, String sourceTableName) {
        // 生成配置文件字符串
        ArrayList<String> columnTextInSelect = new ArrayList<>();
        for (CleansingTaskBean.CleansingRule rule : taskBean.rules) {
            String savedOption = rule.fieldCleansing.savedOption;
            switch (Op.getItemByOption(savedOption)) {
                case OP_TRIM:
                    String functionAndRename = "regexp_replace(%s, %s, '') %s";
                    String regex = StringEscapeUtils.escapeJava("'(^\\\\s+|\\\\s+$)'");
                    String column = String.format(functionAndRename, rule.fieldName, regex, rule.fieldName);
                    columnTextInSelect.add(column);
                    break;
                case OP_UPPER:
                    functionAndRename = "upper(%s) %s";
                    column = String.format(functionAndRename, rule.fieldName);
                    columnTextInSelect.add(column);
                    break;
                default:
                    column = rule.fieldName;
                    columnTextInSelect.add(column);
            }
        }
        String joinedSelectClause = String.join(",", columnTextInSelect);

        StringBuilder finalConfig = new StringBuilder();
        // ---------------------------------------------------------------------------------
        finalConfig.append(ConfigTemplate.envConfigTemplate);

        // ---------------------------------------------------------------------------------
        HashMap<String, String> kafkaMap = new HashMap<>();
        // 加sourceId的方案 schema_id_table
//        kafkaMap.put("topicName", sourceSchemaName + "_" + taskBean.inputDataSource + "_" + taskBean.inputTableName);
        // 不加的方案 schema_table
        kafkaMap.put("topicName", StringUtils.lowerCase(sourceSchemaName + "_" + sourceTableName));
        kafkaMap.put("brokers", kafkaBrokers);
        kafkaMap.put("resultTableName", StringUtils.lowerCase(sourceSchemaName + "_" + sourceTableName));
        StringSubstitutor kafkaSubstitutor = new StringSubstitutor(kafkaMap);
        String kafkaSourceConfig = kafkaSubstitutor.replace(ConfigTemplate.kafkaSourceConfigTemplate);

        HashMap<String, String> sourceMap = new HashMap<>();
        sourceMap.put("source", kafkaSourceConfig);
        StringSubstitutor sourceSubstitutor = new StringSubstitutor(sourceMap);
        String sourceConfig = sourceSubstitutor.replace(ConfigTemplate.sourceConfigTemplate);

        // ------------------------------------------------------------------------------------
        HashMap<String, String> extractBinlogTransformMap = new HashMap<>();
        extractBinlogTransformMap.put("resultTableName", StringUtils.lowerCase(sourceSchemaName + "_" + sourceTableName));
        StringSubstitutor extractBinlogTransformConfigSubstitutor = new StringSubstitutor(extractBinlogTransformMap);
        String extractBinlogTransformConfig = extractBinlogTransformConfigSubstitutor
                .replace(ConfigTemplate.extractBinlogTransformConfigTemplate);

        HashMap<String, String> structedBinlogTransformMap = new HashMap<>();
        ArrayList<String> fieldNameList = new ArrayList<>();
        for (CleansingTaskBean.CleansingRule rule : taskBean.rules) {
            fieldNameList.add(rule.fieldName + ":string");
        }
        String schemaFieldPart = String.join(",", fieldNameList);
        StringBuilder schema = new StringBuilder().append("struct<").append(schemaFieldPart).append(">");
        structedBinlogTransformMap.put("schema", schema.toString());
        StringSubstitutor structedBinlogTransformConfigSubstitutor = new StringSubstitutor(structedBinlogTransformMap);
        String structedBinlogTransformConfig = structedBinlogTransformConfigSubstitutor
                .replace(ConfigTemplate.structedBinlogTransformConfigTemplate);

        String formattedBinlogTransformConfig = ConfigTemplate.formattedBinlogTransformConfigTemplate;

        HashMap<String, String> cleanedBinlogTransformMap = new HashMap<>();
        cleanedBinlogTransformMap.put("selectClause", joinedSelectClause);
        StringSubstitutor cleanedBinlogTransformConfigSubstitutor = new StringSubstitutor(cleanedBinlogTransformMap);
        String cleanedBinlogTransformConfig = cleanedBinlogTransformConfigSubstitutor
                .replace(ConfigTemplate.cleanedBinlogTransformConfigTemplate);

        StringBuilder sqlTemplates = new StringBuilder().append(extractBinlogTransformConfig)
                .append(structedBinlogTransformConfig)
                .append(formattedBinlogTransformConfig)
                .append(cleanedBinlogTransformConfig);
        HashMap<String, String> transformConfigMap = new HashMap<>();
        transformConfigMap.put("transform", sqlTemplates.toString());
        StringSubstitutor transformConfigSubstitutor = new StringSubstitutor(transformConfigMap);
        String transformConfig = transformConfigSubstitutor.replace(ConfigTemplate.transformConfigTemplate);

        // ----------------------------------------------------------------------------------------
        HashMap<String, String> esMap = new HashMap<>();
        esMap.put("hostNames", esHostName);
        esMap.put("indexName", StringUtils.lowerCase(sourceSchemaName + "_" + sourceTableName));
        StringSubstitutor esSinkConfigSubstitutor = new StringSubstitutor(esMap);
        String esSinkConfig = esSinkConfigSubstitutor.replace(ConfigTemplate.esSinkConfigTemplate);

        HashMap<String, String> sinkMap = new HashMap<>();
        sinkMap.put("sink", esSinkConfig);
        StringSubstitutor sinkConfigSubstitutor = new StringSubstitutor(sinkMap);
        String sinkConfig = sinkConfigSubstitutor.replace(ConfigTemplate.sinkConfigTemplate);

        finalConfig.append(sourceConfig).append(transformConfig).append(sinkConfig);

        logger.info(finalConfig.toString());
        return finalConfig.toString();
    }

    @Override
    public Integer insertNewCleansingTask(CleansingTask cleansingTaskModel) {
        return cleansingTaskMapper.insertCleansingTask(cleansingTaskModel);
    }

    public static void main(String[] args) {
        String timeString = "2022-11-10T10:39:22.913GMT";
        timeString = StringUtils.substringBefore(timeString, "GMT");
        LocalDateTime localDateTime = LocalDateTime.parse(timeString);
    }

    @Override
    public List<CleansingTaskBean> fetchAllCleansingTask() {
        try {
            // spark history server 与 yarn history server 不一致，改成使用yarn
//            List<SparkApplication> sparkApps = SparkHTTPClient.getAllApplications();
//            HashMap<String, SparkApplication> sparkAppsMap = new HashMap<>(); // appId: sparkApp
//            for (SparkApplication sparkApp : sparkApps) {
//                sparkAppsMap.put(sparkApp.getId(), sparkApp);
//            }

            // 使用yarn
            JsonRootBean bean = SparkHTTPClient.getAllYarnApplications();
            List<App> sparkApps = bean.getApps().getApp();
            HashMap<String, App> sparkAppsMap = new HashMap<>(); // appId: sparkApp
            for (App sparkApp : sparkApps) {
                sparkAppsMap.put(sparkApp.getId(), sparkApp);
            }
            // 数据库中的清洗任务
            List<CleansingTask> allCleansingTaskInDB = cleansingTaskMapper.findAllCleansingTask();
            for (CleansingTask cleansingTaskInDB : allCleansingTaskInDB) {
                Integer id = cleansingTaskInDB.getId();
                String appId = cleansingTaskInDB.getAppId();
                // 数据库中的任务在spark中也存在
                if (sparkAppsMap.containsKey(appId)) {
                    CleansingTask task = cleansingTaskMapper.findCleansingTaskByIncrementalId(id);
                    App sparkApplication = sparkAppsMap.get(appId);
                    if (task != null) {
                        if (sparkApplication.getState().equals("FINISHED")
                            || sparkApplication.getState().equals("FAILED")
                            || sparkApplication.getState().equals("KILLED")) {
                            task.setStartTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(sparkApplication.getStartedTime()), TimeZone.getDefault().toZoneId()));
                            task.setCompleteTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(sparkApplication.getFinishedTime()), TimeZone.getDefault().toZoneId()));
                            task.setStatus(2);  // 状态设置为已停止
                        } else {
                            task.setStartTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(sparkApplication.getStartedTime()), TimeZone.getDefault().toZoneId()));
                            task.setStatus(1);  // 状态设置为运行中
                        }
                        cleansingTaskMapper.updateCleansingTaskStatusById(task);
                    } else {
                        logger.error("按照 [taskId] " + id + " [appId] " + appId + " 取出的清洗任务为null");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<CleansingTask> taskModelList = cleansingTaskMapper.findAllCleansingTask();
        List<CleansingTaskBean> taskBeanList = new ArrayList<>();
        for (CleansingTask taskModel : taskModelList) {
            CleansingTaskBean taskBean = new CleansingTaskBean();
            taskBean.id = String.valueOf(taskModel.getId());
            taskBean.taskName = taskModel.getTaskName();
            taskBean.appId = taskModel.getAppId();
            taskBean.startTime = taskModel.getStartTime();
            taskBean.completeTime = taskModel.getCompleteTime();
            taskBean.inputDataSource = String.valueOf(taskModel.getInputDataSource());
            taskBean.inputDataSourceType = taskModel.getInputType();

            // 显示列表中的表名
            if (taskBean.inputDataSourceType.equals("MySQL")) {
                // 数据源是MySQL时显示数据库名+表名
                MySQLSource mySQLSource = dataSourceService.findMySQLSourceById(taskBean.inputDataSource);
                taskBean.inputTableName = mySQLSource.getName();
                taskBean.inputTableName = taskBean.inputTableName + "：" + taskModel.getInputDataSourceTable();
            } else if (taskBean.inputDataSourceType.equals("Spider")) {
                // 数据源是Spider时，要先查到爬虫名称，由于目前一个爬虫对应一张表，所以，给用户看的是爬虫的名字，而不是表名
                SpiderSource spiderSource = dataSourceService.findSpiderSourceById(taskBean.inputDataSource);
                taskBean.inputTableName = spiderSource.getName();
            } else if (taskBean.inputDataSourceType.equals("Pspace")) {
                // 数据源是Pspace时，由于每个Pspace种类对应一个表，所以，给用户看的是种类
                taskBean.inputTableName = PSpaceTableRelation.pSpaceTableTypeMap.get(taskModel.getInputDataSourceTable());
            }

            taskBean.status = String.valueOf(taskModel.getStatus());
            taskBean.outputTableId = String.valueOf(taskModel.getOutputTable());
            taskBean.publisher = taskModel.getPublisher();
            taskBean.configLocation = taskModel.getConfigLocation();
            if (taskBean.completeTime == null) {
                taskBean.duration = "未完成";
            } else {
                Duration duration = Duration.between(taskBean.startTime, taskBean.completeTime);
                long seconds = duration.getSeconds();
                taskBean.duration = formatSecondsForDuration(seconds);
            }
            taskBeanList.add(taskBean);
        }
        return taskBeanList;
    }

    public static String formatSecondsForDuration(long time) {
        int temp = (int) time;
        int hh = temp / 3600;
        int mm = (temp % 3600) / 60;
        int ss = (temp % 3600) % 60;
        return (hh < 10 ? ("0" + hh) : hh) + ":" +
                (mm < 10 ? ("0" + mm) : mm) + ":" +
                (ss < 10 ? ("0" + ss) : ss);
    }

    private DatabaseConnectionSource getDataSourceForFetchingSchema(String schemaName) {
        final String connectionUrl = dataSourceAddress + schemaName;
        final DatabaseConnectionSource dataSource =
                DatabaseConnectionSources.newDatabaseConnectionSource(
                        connectionUrl, new MultiUseUserCredentials(dataSourceUser, dataSourcePassword));
        return dataSource;
    }

    @Override
    public DatabaseConnectionSource getDataSourceForFetchingSchema(String schemaName, String host, String port) {
        final String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + schemaName;
        final DatabaseConnectionSource dataSource =
                DatabaseConnectionSources.newDatabaseConnectionSource(
                        connectionUrl, new MultiUseUserCredentials(dataSourceUser, dataSourcePassword));
        return dataSource;
    }

    @Override
    public Integer insertCleansingTaskAndSparkApp(CleansingTaskSparkApp cleansingTaskSparkApp) {
        return cleansingTaskSparkAppMapper.insertCleansingTaskAndSparkApp(cleansingTaskSparkApp);
    }

    @Override
    public boolean deleteCleansingTaskByTaskId(Integer taskId) {
        int line = cleansingTaskMapper.deleteCleansingTaskById(taskId);
        return true;
    }

    @Override
    public boolean deleteCleansingTaskAndSparkAppByTaskId(Integer taskId) {
        cleansingTaskMapper.deleteCleansingTaskById(taskId);
        return true;
    }

    @Override
    public CleansingTask findCleansingTaskById(Integer taskId) {
        return cleansingTaskMapper.findCleansingTaskByIncrementalId(taskId);
    }

    @Override
    public boolean updateCleansingTaskById(CleansingTask cleansingTask) {
        return cleansingTaskMapper.updateCleansingTaskStatusById(cleansingTask);
    }

    @Override
    public Integer insertCleansingTaskAndCanalInstance(CleansingTaskCanalInstance cleansingTaskCanalInstance) {
        return cleansingTaskCanalInstanceMapper.insertCleansingTaskAndCanalInstance(cleansingTaskCanalInstance);
    }

    @Override
    public Integer updateCleansingTaskAndCanalInstance(CleansingTaskCanalInstance cleansingTaskCanalInstance) {
        return cleansingTaskCanalInstanceMapper.updateCleansingTaskAndCanalInstance(cleansingTaskCanalInstance);
    }

    @Override
    public CleansingTaskCanalInstance findCleansingTaskAndCanalInstanceByTaskId(Integer cleansingTaskId) {
        return cleansingTaskCanalInstanceMapper.findCleansingTaskAndCanalInstance(cleansingTaskId);
    }

    @Override
    public boolean deleteCleansingTaskAndCanalInstance(Integer taskId) {
        int line = cleansingTaskCanalInstanceMapper.deleteCleansingTaskAndCanalInstanceById(taskId);
        if (line == -1) {
            return false;
        }
        return true;
    }

}


class EsIndexCreator {
    static void createEsIndex() {

    }

    public static void main(String[] args) {

    }
}

