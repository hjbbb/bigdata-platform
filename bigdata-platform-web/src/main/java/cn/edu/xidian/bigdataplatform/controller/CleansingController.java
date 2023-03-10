package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.CleansingService;
import cn.edu.xidian.bigdataplatform.DataSourceService;
import cn.edu.xidian.bigdataplatform.IngestionService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.base.ResultCode;
import cn.edu.xidian.bigdataplatform.bean.*;
import cn.edu.xidian.bigdataplatform.canal.*;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTask;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskCanalInstance;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskSparkApp;
import cn.edu.xidian.bigdataplatform.mybatis.entity.ConfigWriter;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.PSpace;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;
import cn.edu.xidian.bigdataplatform.seatunnel.SeatunnelStarter;
import cn.edu.xidian.bigdataplatform.vo.DataSourceListForCleansing;
import cn.edu.xidian.bigdataplatform.vo.InputTableVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.tools.utility.SchemaCrawlerUtility;
import us.fatehi.utility.datasource.DatabaseConnectionSource;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static cn.edu.xidian.bigdataplatform.seatunnel.SeatunnelStarter.appId;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-10
 * @description:
 */
@RestController
public class CleansingController {
    public static final String basePath = "/usr/local/seatunnel-xd/config/";
    private static Logger logger = LoggerFactory.getLogger(CleansingController.class);

    @Autowired
    private CleansingService cleansingService;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private IngestionService ingestionService;

    @GetMapping("/api/getInputDatabase")
    public Result getInputDatabase() {
        DataSourceListForCleansing dataSourceListForCleansing = new DataSourceListForCleansing();
        List<MySQLSource> mySQLSources = dataSourceService.getMySQLSources();
        List<SpiderSource> spiderSources = dataSourceService.getSpiderSources();
        List<DataSourceListForCleansing.MySQLSourceInCleansing> mysqlList = new ArrayList<>();
        for (MySQLSource mySQLSource : mySQLSources) {
            List<String> tableNameListInSchema = cleansingService.getTableNameListInSchema(mySQLSource.getSchemaName());
            DataSourceListForCleansing.MySQLSourceInCleansing source = new DataSourceListForCleansing.MySQLSourceInCleansing();
            source.setTables(tableNameListInSchema);
            source.setMySQLSource(mySQLSource);
            mysqlList.add(source);
        }
        dataSourceListForCleansing.setMysqlList(mysqlList);

        List<DataSourceListForCleansing.SpiderSourceInCleansing> spiderList = new ArrayList<>();
        for (SpiderSource spiderSource : spiderSources) {
            DataSourceListForCleansing.SpiderSourceInCleansing source = new DataSourceListForCleansing.SpiderSourceInCleansing();
            String spiderName = spiderSource.getName();
            List<String> tables = new ArrayList<>();
            tables.add(SpiderTableRelation.spiderTableRelation.get(spiderName));
            source.setTables(tables);
            source.setSpiderSource(spiderSource);
            spiderList.add(source);
        }
        dataSourceListForCleansing.setSpiderList(spiderList);

        // TODO ??????PSpace
        List<PSpaceDTO> pSpaceSources = dataSourceService.getPSpaceSources();
        return Result.success(dataSourceListForCleansing);
    }

    // ??????????????????????????????????????????????????????input table???
    @GetMapping("/api/getAllInputTable")
    public Result getAllInputTable() {
        List<SpiderSourceDTO> spiderTasks = ingestionService.fetchAllSpiderSources();
        List<MySQLTaskDTO> mySQLTasks = ingestionService.fetchAllMySQLTasks();
        List<InputTableVO> inputTables = new ArrayList<>();
        for (SpiderSourceDTO spiderTask : spiderTasks) {
            InputTableVO inputTable = new InputTableVO();
            inputTable.setId(spiderTask.getId());
            inputTable.setName(spiderTask.getName());
            inputTable.setType("Spider");
            inputTables.add(inputTable);
        }
        for (MySQLTaskDTO mySQLTask : mySQLTasks) {
            InputTableVO inputTable = new InputTableVO();
            inputTable.setId(mySQLTask.getId());
            inputTable.setName(mySQLTask.getName());
            inputTable.setType("MySQL");
            inputTables.add(inputTable);
        }
        return Result.success(inputTables);
    }

    //	????????????????????????
    @GetMapping("/api/getConfigTableData")
    public Result getConfigTableData(@RequestParam(name="sourceId") String sourceId,
                                     @RequestParam(name = "sourceType") String sourceType,
                                     @RequestParam(name="sourceTableName") String sourceTableName) {
        CleansingTaskBean task = new CleansingTaskBean();
        if (sourceType.equals("MySQL")) {
//			MySQLTaskDTO ingestionTask = ingestionService.findMySQLById(Integer.parseInt(sourceId));
            MySQLSource mySQLSource = dataSourceService.findMySQLSourceById(sourceId);
            if (mySQLSource == null) {
                return Result.failed(ResultCode.FAILED);
            }
            String schemaName = mySQLSource.getSchemaName();
            String tableName = sourceTableName;
            List<CleansingTaskBean.SingleColumn> columnList = cleansingService.fetchSchema(schemaName, tableName);
            if (columnList.isEmpty()) {
                return Result.failed(ResultCode.FAILED);
            }
            task.columnList = columnList;
            task.inputDataSource = sourceId;
            task.inputDataSourceType = sourceType;
            task.rules = new ArrayList<CleansingTaskBean.CleansingRule>();
            for (CleansingTaskBean.SingleColumn column : columnList) {
                CleansingTaskBean.CleansingRule rule = new CleansingTaskBean.CleansingRule();
                rule.fieldName = column.columnName;
                rule.fieldOriginalType = column.databaseSpecificType;
                rule.fieldCleansing = new CleansingTaskBean.FieldCleansing();
                rule.fieldCleansing.options = cleansingService.createOps(column);
                rule.fieldCleansing.selectedOption = cleansingService.createDefaultOps(column).getOption();
                rule.fieldCleansing.savedOption = cleansingService.createDefaultOps(column).getOption();
                rule.isEditting = false;
                logger.info(rule.toString());
                task.rules.add(rule);
            }
            // 23.1.9 ??????
            task.sourceConfig = "";
            task.transformConfig = "";
            task.sinkConfig = "";
            // ??????????????????
            task.filterIfNull = false;   //????????????
            task.trimIfString = false;   // ??????????????????
            task.deduplicateByPk = false;// ??????????????????
        } else if (sourceType.equals("Spider")) {
            // ?????????????????????
            SpiderSource spiderSourceById = dataSourceService.findSpiderSourceById(sourceId);
            // ???????????????????????????
            // ???????????????Schema
            String schemaName = "logistics";
            String tableName = SpiderTableRelation.spiderTableRelation.get(spiderSourceById.getName());
            List<CleansingTaskBean.SingleColumn> columnList = cleansingService.fetchSchema(schemaName, tableName);
            if (columnList.isEmpty()) {
                return Result.failed(ResultCode.FAILED);
            }
            task.columnList = columnList;
            task.inputDataSource = sourceId;
            task.inputDataSourceType = sourceType;
            task.rules = new ArrayList<CleansingTaskBean.CleansingRule>();
            for (CleansingTaskBean.SingleColumn column : columnList) {
                CleansingTaskBean.CleansingRule rule = new CleansingTaskBean.CleansingRule();
                rule.fieldName = column.columnName;
                rule.fieldOriginalType = column.databaseSpecificType;
                rule.fieldCleansing = new CleansingTaskBean.FieldCleansing();
                rule.fieldCleansing.options = cleansingService.createOps(column);
                rule.fieldCleansing.selectedOption = cleansingService.createDefaultOps(column).getOption();
                rule.fieldCleansing.savedOption = cleansingService.createDefaultOps(column).getOption();
                rule.isEditting = false;
                logger.info(rule.toString());
                task.rules.add(rule);
            }
            // 23.1.9 ??????
            task.sourceConfig = "";
            task.transformConfig = "";
            task.sinkConfig = "";
            // ??????????????????
            task.filterIfNull = false;   //????????????
            task.trimIfString = false;   // ??????????????????
            task.deduplicateByPk = false;// ??????????????????
        } else if (sourceType.equals("Pspace")) {
            // ?????????????????????
            // sourceTable
            // ???????????????????????????
            // ???????????????Schema
            String schemaName = "pSpaceData";
            String tableName = PSpaceTableRelation.pSpaceTypeTableMap.get(sourceTableName);
            List<CleansingTaskBean.SingleColumn> columnList = cleansingService.fetchSchema(schemaName, tableName);
            if (columnList.isEmpty()) {
                return Result.failed(ResultCode.FAILED);
            }
            task.columnList = columnList;
            task.inputDataSource = sourceId;
            task.inputDataSourceType = sourceType;
            task.rules = new ArrayList<CleansingTaskBean.CleansingRule>();
            for (CleansingTaskBean.SingleColumn column : columnList) {
                CleansingTaskBean.CleansingRule rule = new CleansingTaskBean.CleansingRule();
                rule.fieldName = column.columnName;
                rule.fieldOriginalType = column.databaseSpecificType;
                rule.fieldCleansing = new CleansingTaskBean.FieldCleansing();
                rule.fieldCleansing.options = cleansingService.createOps(column);
                rule.fieldCleansing.selectedOption = cleansingService.createDefaultOps(column).getOption();
                rule.fieldCleansing.savedOption = cleansingService.createDefaultOps(column).getOption();
                rule.isEditting = false;
                logger.info(rule.toString());
                task.rules.add(rule);
            }
            // 23.1.9 ??????
            task.sourceConfig = "";
            task.transformConfig = "";
            task.sinkConfig = "";
            // ??????????????????
            task.filterIfNull = false;   //????????????
            task.trimIfString = false;   // ??????????????????
            task.deduplicateByPk = false;// ??????????????????
        }
        return Result.success(task);
    }

    private Long requestCanalAddNewInstance(String sourceName,
                                            CleansingTaskBean task,
                                            String dbAddress,
                                            String wrongDbAddress,
                                            String schemaName,
                                            String tableName,
                                            String canalPropDBUsername,
                                            String canalPropDBPassword,
                                            Long itemId) throws IOException {
        InstanceCreate instance = new InstanceCreate();
        instance.setClusterServerId(CanalAdminHTTPClient.serverClusterId);
        InputStream is = new FileInputStream(CanalAdminHTTPClient.instancePropertyPath);
        Properties p = new Properties();
        p.load(is);
        String kafkaTopic = schemaName + "\\." + tableName;
        p.setProperty("canal.mq.dynamicTopic", StringUtils.lowerCase(kafkaTopic));  // ?????????????????????topic
        p.setProperty("canal.instance.dbUsername", canalPropDBUsername);
        p.setProperty("canal.instance.dbPassword", canalPropDBPassword);
        p.setProperty("canal.instance.master.address", dbAddress);
        StringWriter writer = new StringWriter();
        p.store(writer, "");
        String propContent = writer.getBuffer().toString();
        String fixedPropContent = StringUtils.replaceOnce(propContent, wrongDbAddress, dbAddress);
        String instanceName = sourceName + tableName; // ?????????????????? ???????????????+table
        instance.setName(instanceName);
        instance.setContent(fixedPropContent);
        String result = CanalAdminHTTPClient.add(instance);

        // ??????????????????
        CanalResponse<InstanceList> listResponse = CanalAdminHTTPClient.list();
        InstanceList instanceList = listResponse.getData();
        List<InstanceConfig> items = instanceList.getItems();
        for (InstanceConfig item : items) {
            String itemName = item.getName();
            if (itemName.equals(instanceName)) {
                // ??????????????????
                String runningStatus = item.getRunningStatus();
                itemId = item.getId();
                if (runningStatus.equals("1")) {   // canalAdmin??????1????????????0?????????
                    break;
                } else {
                    // ??????????????????????????????
                    logger.debug("????????????????????????????????????????????????");
                    CanalAdminHTTPClient.start(Math.toIntExact(itemId));  // ??????long ?????????int???????????????
                }
            } else {
                // ??????????????????
            }
        }
        return itemId;
    }

    private void createESIndex(String sourceSchemaName, String sourceTableName, ArrayList<CleansingTaskBean.SingleColumn> columnList) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("172.17.0.2", 9200, "http")));

        String esIndexName = StringUtils.lowerCase(sourceSchemaName) + "_" + StringUtils.lowerCase(sourceTableName);
        // ?????????????????????????????????????????????????????????ConnectionException???????????????????????????????????????
        if (!EsIndexCreator.isIndexExists(esIndexName)) {
            CreateIndexRequest request = new CreateIndexRequest(esIndexName);
            request.settings(Settings.builder()
                    .put("index.number_of_shards", 3)
                    .put("index.number_of_replicas", 1)
                    .put("index.max_result_window", 1000000000));

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {
                    for (CleansingTaskBean.SingleColumn column : columnList) {
                        builder.startObject(column.columnName);
                        {
                            builder.field("type", "text");
//                                    .field("analyzer", "ik_smart")
//                                    .field("search_analyzer", "ik_max_word");
                        }
                        builder.endObject();
                    }
                }
                builder.endObject();
            }
            builder.endObject();

            request.mapping("_doc", builder);
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            logger.debug("create index");
            logger.debug(String.valueOf(createIndexResponse.isAcknowledged()));
            client.close();
        }
    }

    @PostMapping("/api/submitConfig")
    public Result submitConfig(@RequestBody CleansingTaskBean task) throws IOException, InterruptedException {
        String sourceSchemaName = "";
        String sourceTableName = "";
        Long itemId = -1L;
        if (task.inputDataSourceType.equals("MySQL")) {
            MySQLSource mySQLSource = dataSourceService.findMySQLSourceById(task.inputDataSource);
            sourceSchemaName = mySQLSource.getSchemaName();
            sourceTableName = task.inputDataSourceTableName;
            // ???????????????canalAdmin?????????????????????binlog?????????
            String dbAddress = mySQLSource.getHost()+":"+mySQLSource.getPort();
            String wrongDbAddress = mySQLSource.getHost()+"\\:" + mySQLSource.getPort();
            itemId = requestCanalAddNewInstance(mySQLSource.getName(), task, dbAddress, wrongDbAddress, sourceSchemaName, sourceTableName,
                    mySQLSource.getUsername(), mySQLSource.getPassword(), itemId);

            // ????????????????????????????????????
            String host = mySQLSource.getHost();
            String port = String.valueOf(mySQLSource.getPort());
            DatabaseConnectionSource dbConnection = cleansingService.getDataSourceForFetchingSchema(sourceSchemaName, host, port);
            final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions();
            final Catalog catalog = SchemaCrawlerUtility.getCatalog(dbConnection, options);
            Collection<Schema> schemas = catalog.getSchemas();
            if (schemas == null || schemas.isEmpty()) {
                return null;
            }
            Collection<Table> tables = catalog.getTables();
            ArrayList<CleansingTaskBean.SingleColumn> columnList = new ArrayList<>();
            for (Table table : tables) {
                if (table.getName().equals(sourceTableName) && table.getSchema().getFullName().equals(sourceSchemaName)) {
                    List<Column> columns = table.getColumns();
                    for (Column column : columns) {
                        CleansingTaskBean.SingleColumn singleColumn = new CleansingTaskBean.SingleColumn();
                        singleColumn.columnName = column.getName();
                        singleColumn.databaseSpecificType = column.getColumnDataType().getDatabaseSpecificTypeName();
                        columnList.add(singleColumn);
                    }
                }
            }

//            ??????????????????ES??????
            // ??????es???????????????????????????schema,tablename????????????
            createESIndex(sourceSchemaName, sourceTableName, columnList);

        } else if (task.inputDataSourceType.equals("Spider")) {
            SpiderSource spiderSource = dataSourceService.findSpiderSourceById(task.inputDataSource);
            String schemaName = "logistics";
            sourceSchemaName = schemaName;
            sourceTableName = SpiderTableRelation.spiderTableRelation.get(spiderSource.getName());

            // ???????????????canalAdmin?????????????????????binlog?????????
            String dbAddress = "127.0.0.1"+":"+"3306";
            String wrongDbAddress = "127.0.0.1"+"\\:" + "3306";
            itemId = requestCanalAddNewInstance(
                    spiderSource.getName(),
                    task,
                    dbAddress,
                    wrongDbAddress,
                    schemaName,
                    sourceTableName,
                    CanalAdminHTTPClient.CANAL_ADMIN_DB_USERNAME,
                    CanalAdminHTTPClient.LOGISTICS_DB_PASSWORD,
                    itemId);

            // ????????????????????????????????????
            String host = "127.0.0.1";
            String port = "3306";
            DatabaseConnectionSource dbConnection = cleansingService.getDataSourceForFetchingSchema(sourceSchemaName, host, port);
            final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions();
            final Catalog catalog = SchemaCrawlerUtility.getCatalog(dbConnection, options);
            Collection<Schema> schemas = catalog.getSchemas();
            if (schemas == null || schemas.isEmpty()) {
                return null;
            }
            Collection<Table> tables = catalog.getTables();
            ArrayList<CleansingTaskBean.SingleColumn> columnList = new ArrayList<>();
            for (Table table : tables) {
                if (table.getName().equals(sourceTableName) && table.getSchema().getFullName().equals(sourceSchemaName)) {
                    List<Column> columns = table.getColumns();
                    for (Column column : columns) {
                        CleansingTaskBean.SingleColumn singleColumn = new CleansingTaskBean.SingleColumn();
                        singleColumn.columnName = column.getName();
                        singleColumn.databaseSpecificType = column.getColumnDataType().getDatabaseSpecificTypeName();
                        columnList.add(singleColumn);
                    }
                }
            }

            // ??????????????????es???????????????????????????schema,tablename????????????
            createESIndex(sourceSchemaName, sourceTableName, columnList);
        }  else if (task.inputDataSourceType.equals("Pspace")) {
            String pSpaceDataType = task.inputDataSourceTableName;

            String schemaName = "pSpaceData";
            sourceTableName = PSpaceTableRelation.pSpaceTypeTableMap.get(pSpaceDataType);
            sourceSchemaName = schemaName;

            // ???????????????canalAdmin?????????????????????binlog?????????
            String dbAddress = "127.0.0.1"+":"+"3306";
            String wrongDbAddress = "127.0.0.1"+"\\:" + "3306";
            itemId = requestCanalAddNewInstance(
                    "pSpace",
                    task,
                    dbAddress,
                    wrongDbAddress,
                    schemaName,
                    sourceTableName,
                    CanalAdminHTTPClient.CANAL_ADMIN_DB_USERNAME,
                    CanalAdminHTTPClient.LOGISTICS_DB_PASSWORD,   // pSpace???????????????????????????????????????
                    itemId);

            // ????????????????????????????????????
            String host = "127.0.0.1";
            String port = "3306";
            DatabaseConnectionSource dbConnection = cleansingService.getDataSourceForFetchingSchema(sourceSchemaName, host, port);
            final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions();
            final Catalog catalog = SchemaCrawlerUtility.getCatalog(dbConnection, options);
            Collection<Schema> schemas = catalog.getSchemas();
            if (schemas == null || schemas.isEmpty()) {
                return null;
            }
            Collection<Table> tables = catalog.getTables();
            ArrayList<CleansingTaskBean.SingleColumn> columnList = new ArrayList<>();
            for (Table table : tables) {
                if (table.getName().equals(sourceTableName) && table.getSchema().getFullName().equals(sourceSchemaName)) {
                    List<Column> columns = table.getColumns();
                    for (Column column : columns) {
                        CleansingTaskBean.SingleColumn singleColumn = new CleansingTaskBean.SingleColumn();
                        singleColumn.columnName = column.getName();
                        singleColumn.databaseSpecificType = column.getColumnDataType().getDatabaseSpecificTypeName();
                        columnList.add(singleColumn);
                    }
                }
            }

            // ??????????????????es???????????????????????????schema,tablename????????????
            createESIndex(sourceSchemaName, sourceTableName, columnList);
        }

        // ??????????????????
        String configFromTable = cleansingService.convertTaskToConfigString(task, sourceSchemaName, sourceTableName);
        String configFilePath = basePath + LocalDateTime.now().toString();
        task.outputTableId = String.valueOf(-1);
        try {
            ConfigWriter.writeToExternalConfigFileFromTable(configFromTable, configFilePath);
        } catch (IOException e) {
            logger.error(e.toString());
            return Result.failed(ResultCode.FAILED);
        }
        // ????????????
        // ?????????
//		final String configFilePathDebug = "/usr/local/seatunnel-xd/config/spark.streaming.conf.template";
        Long finalItemId = itemId;
        new Thread() {
            @Override
            public void run() {
                try {
                    logger.info("run seatunnel in another thread " + Thread.currentThread().getName());
                    SeatunnelStarter.run(configFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                // ?????????
//				String s= "application_1670982441562_0001";
            }
        }.start();
        while (SeatunnelStarter.appId == null) {
            Thread.sleep(10000);
        }
        logger.info("???????????? id: " + SeatunnelStarter.appId);
        CleansingTask cleansingTaskModel = new CleansingTask();
        cleansingTaskModel.setAppId(SeatunnelStarter.appId);
        cleansingTaskModel.setTaskName(task.taskName);
        cleansingTaskModel.setInputType(task.inputDataSourceType); // ???????????????MySQL???Pspace???Spider
        cleansingTaskModel.setInputDataSource(Integer.valueOf(task.inputDataSource));
        if (task.inputDataSourceType.equals("MySQL")) {
            cleansingTaskModel.setInputDataSourceTable(task.inputDataSourceTableName);
        } else if (task.inputDataSourceType.equals("Spider")) {
            String spiderName = task.inputDataSourceTableName;
            String tableName = SpiderTableRelation.spiderTableRelation.get(spiderName);
            cleansingTaskModel.setInputDataSourceTable(tableName);
        } else if (task.inputDataSourceType.equals("Pspace")) {
            cleansingTaskModel.setInputDataSourceTable(PSpaceTableRelation.pSpaceTypeTableMap.get(task.inputDataSourceType));
        }
        cleansingTaskModel.setOutputTable(Integer.valueOf(task.outputTableId));
        cleansingTaskModel.setStartTime(LocalDateTime.now());
        cleansingTaskModel.setStatus(1);  // ?????????????????????
        cleansingTaskModel.setPublisher("admin");
        cleansingTaskModel.setConfigLocation(configFilePath);
        Integer line = cleansingService.insertNewCleansingTask(cleansingTaskModel);
        logger.info("insert new cleansing task " + cleansingTaskModel.getId());
        CleansingTaskSparkApp cleansingTaskSparkApp = new CleansingTaskSparkApp();
        cleansingTaskSparkApp.setSparkAppId(SeatunnelStarter.appId);
        cleansingTaskSparkApp.setTaskId(cleansingTaskModel.getId());
        cleansingService.insertCleansingTaskAndSparkApp(cleansingTaskSparkApp);
        CleansingTaskCanalInstance cleansingTaskCanalInstance = new CleansingTaskCanalInstance();
        if (finalItemId == -1L) {
            logger.warn("Canal???InstanceId???????????????");
        }
        cleansingTaskCanalInstance.setCanalInstanceId(finalItemId);
        cleansingTaskCanalInstance.setTaskId(cleansingTaskModel.getId());
        cleansingService.insertCleansingTaskAndCanalInstance(cleansingTaskCanalInstance);
        SeatunnelStarter.appId = null;
        return Result.success(true);
    }

    @GetMapping("/api/getAllDataCleansingTasks")
    public Result getAllDataCleansingTasks() {
        List<CleansingTaskBean> taskBeanList = cleansingService.fetchAllCleansingTask();
        return Result.success(taskBeanList);
    }

    @GetMapping("/api/deleteDataCleansingTask")
    public Result deleteDataCleansingTask(@RequestParam(name="taskId") String taskId) {
        CleansingTask taskById = cleansingService.findCleansingTaskById(Integer.parseInt(taskId));
        if(taskById.getStatus() == 1){
            // ?????????????????????????????????????????????
            new Thread() {
                @Override
                public void run() {
                    List<String> command = new ArrayList<>();
                    command.add("/usr/local/hadoop/bin/yarn");
                    command.add("application");
                    command.add("-kill");
                    command.add(taskById.getAppId());
                    ProcessBuilder builder = new ProcessBuilder(command);
                    builder.redirectErrorStream(true);
                    System.out.println("process start");
                    try {
                        Process process = builder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        cleansingService.deleteCleansingTaskByTaskId(Integer.valueOf(taskId));
        cleansingService.deleteCleansingTaskAndSparkAppByTaskId(Integer.valueOf(taskId));
        cleansingService.deleteCleansingTaskAndCanalInstance(Integer.valueOf(taskId));
        return Result.success(true);
    }

    @GetMapping("/api/startDataCleansingTask")
    public Result startDataCleansingTask(@RequestParam(name="taskId") String taskId) {
        SeatunnelStarter.appId = null;
        // ??????canal?????????Id????????????????????????????????????CanalId
        CleansingTaskCanalInstance cleansingTaskCanalInstanceById = cleansingService.findCleansingTaskAndCanalInstanceByTaskId(Integer.valueOf(taskId));
        final long canalInstanceId = cleansingTaskCanalInstanceById.getCanalInstanceId();
        try {
            CanalAdminHTTPClient.start(Math.toIntExact(canalInstanceId));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failed(ResultCode.START_CANAL_INSTANCE_FAILED);
        }

        CleansingTask taskById = cleansingService.findCleansingTaskById(Integer.valueOf(taskId));
        logger.debug("??????????????????????????? Id: " + taskById);
        String configLocation = taskById.getConfigLocation();
        logger.debug("?????????????????????????????????????????????: " + configLocation);
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("run seatunnel in another thread " + Thread.currentThread().getName());
                    SeatunnelStarter.run(configLocation);
                    System.out.println("accept " + SeatunnelStarter.appId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        while (SeatunnelStarter.appId == null) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("???????????? id: " + SeatunnelStarter.appId);
        taskById.setAppId(SeatunnelStarter.appId);  // ??????Spark App Id
        taskById.setStatus(1);
        taskById.setConfigLocation(configLocation);
        // ??????????????????
        boolean updateSuccess = cleansingService.updateCleansingTaskById(taskById);
        logger.info("update cleansing task "+ appId + updateSuccess);
        // ?????????????????????spark???????????????
        CleansingTaskSparkApp cleansingTaskSparkApp = new CleansingTaskSparkApp();
        cleansingTaskSparkApp.setSparkAppId(appId);
        cleansingTaskSparkApp.setTaskId(taskById.getId());
        cleansingService.insertCleansingTaskAndSparkApp(cleansingTaskSparkApp);
        // ??????canal??????????????????????????????
        CleansingTaskCanalInstance cleansingTaskCanalInstance = new CleansingTaskCanalInstance();
        cleansingTaskCanalInstance.setTaskId(taskById.getId());
        cleansingTaskCanalInstance.setCanalInstanceId(canalInstanceId);
        cleansingService.updateCleansingTaskAndCanalInstance(cleansingTaskCanalInstance);
        return Result.success(true);
    }

    @GetMapping("/api/stopDataCleansingTask")
    public Result stopDataCleansingTask(@RequestParam(name="taskId") String taskId) {
        CleansingTask taskById = cleansingService.findCleansingTaskById(Integer.parseInt(taskId));
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                logger.debug("trying to stop [taskId] " + taskId);
                List<String> command = new ArrayList<>();
                command.add("/usr/local/hadoop/bin/yarn");
                command.add("application");
                command.add("-kill");
                command.add(taskById.getAppId());
                ProcessBuilder builder = new ProcessBuilder(command);
                builder.redirectErrorStream(true);
                logger.debug(String.join(" ", command));
                System.out.println("process start");
                try {
                    Process process = builder.start();
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String oneLineOfLog;
                    while ((oneLineOfLog = reader.readLine()) != null) {
                        logger.debug(oneLineOfLog);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        taskById.setStatus(2);
        cleansingService.updateCleansingTaskById(taskById);
        return Result.success(true);
    }

    private String opEnumToString(CleansingTaskBean.Op operation) {
        switch (operation) {
            case OP_EQ:
                return "=";
            case OP_NE:
                return "!=";
            case OP_GT:
                return ">";
            case OP_GTE:
                return ">=";
            case OP_LT:
                return "<";
            case OP_LTE:
                return "<=";
            default:
                return "";
        }
    }


}
