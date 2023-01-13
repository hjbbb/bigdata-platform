package cn.edu.xidian.bigdataplatform.impl;

import cn.edu.xidian.bigdataplatform.IngestionService;
import cn.edu.xidian.bigdataplatform.bean.*;
import cn.edu.xidian.bigdataplatform.mybatis.entity.MySQLTask;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.MySQLTaskMapper;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.SpiderSourceMapper;
import cn.edu.xidian.bigdataplatform.spider.SpiderManager;
import cn.edu.xidian.platform.ExportPspaceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-18
 * @description:
 */
@Service
public class IngestionServiceImpl implements IngestionService {
    private static final Logger logger = LoggerFactory.getLogger(IngestionServiceImpl.class);
    private static final String DATAX_JOB_PATH = "G:\\dataX\\job";

    @Autowired
    private SpiderSourceMapper spiderSourceMapper;

    @Autowired
    private MySQLTaskMapper mySQLTaskMapper;

    @Autowired
    private SpiderManager spiderManager;

    //start   爬虫采集
    @Override
    public SpiderSourceDTO launchSpider(SpiderSourceDTO spiderSource) {
        boolean isRunning = spiderManager.probeSpiderByName(spiderSource.getName());
        // 已经启动，直接返回
        if (isRunning) {
            spiderSource.setStatus("1");
            return spiderSource;
        }

        if(spiderSource.getFrequency()!=null && spiderSource.getMinute()!=null && spiderSource.getHour()!= null){//定时爬虫
            String spiderName = spiderSource.getName();
            String minute = spiderSource.getMinute();
            String hour = spiderSource.getHour();
            String frequency = spiderSource.getFrequency();
            String value = spiderSource.getValue();
            spiderManager.launchSpiderTimingUsingShell(spiderName,minute,hour,frequency,value);
        }else{//普通爬虫
            spiderManager.launchSpiderUsingShell(spiderSource.getName());
            SpiderFinished(spiderSource);//检测爬虫是否结束
        }
        boolean isRunningAfterLaunch = spiderManager.probeSpiderByName(spiderSource.getName());
        if (isRunningAfterLaunch) {
            // 更新数据库状态
            SpiderSource spiderSourceModel = new SpiderSource();
            spiderSourceModel.setId(Integer.parseInt(spiderSource.getId()));
            spiderSourceModel.setName(spiderSource.getName());
            spiderSourceModel.setWebsite(spiderSource.getWebsite());
            spiderSourceModel.setCreateTime(spiderSource.getCreateTime());
            spiderSourceModel.setStatus("1");
            spiderSourceModel.setSink(spiderSource.getSink());
            spiderSourceModel.setDescription(spiderSource.getDescription());
            spiderSourceModel.setMinute(spiderSource.getMinute());
            spiderSourceModel.setHour(spiderSource.getHour());
            spiderSourceModel.setFrequency(spiderSource.getFrequency());
            spiderSourceModel.setValue(spiderSource.getValue());
            boolean isUpdateSuccess = spiderSourceMapper.updateSpiderSourceStatus(spiderSourceModel);
            if (isUpdateSuccess) {
                // 更新状态成功，返回值中的status置为运行中
                spiderSource.setStatus("1");
            }
        } else {
            spiderSource.setStatus("2");
        }
        return spiderSource;
    }
    //线程 检查爬虫是否停止
    public void SpiderFinished(SpiderSourceDTO spiderSource) {
        Thread thread = new Thread(){
            public volatile boolean flag = true;
            @Override
            public void run(){
                while (flag) {
                    flag = spiderManager.probeSpiderByName(spiderSource.getName());
                }
                SpiderSource spiderSourceModel = new SpiderSource();
                spiderSourceModel.setId(Integer.parseInt(spiderSource.getId()));
                spiderSourceModel.setName(spiderSource.getName());
                spiderSourceModel.setWebsite(spiderSource.getWebsite());
                spiderSourceModel.setCreateTime(spiderSource.getCreateTime());
                spiderSourceModel.setStatus("2");
                spiderSourceModel.setSink(spiderSource.getSink());
                spiderSourceModel.setDescription(spiderSource.getDescription());
                spiderSourceModel.setMinute(spiderSource.getMinute());
                spiderSourceModel.setHour(spiderSource.getHour());
                spiderSourceModel.setFrequency(spiderSource.getFrequency());
                spiderSourceModel.setValue(spiderSource.getValue());
                boolean isUpdateSuccess = spiderSourceMapper.updateSpiderSourceStatus(spiderSourceModel);
                if (isUpdateSuccess) {
                    spiderSource.setStatus("2");// 更新状态成功，返回值中的status置为停止
                }
            }
        };
        thread.start();
    }

    @Override
    public int deleteSpider(String id){
        int data = spiderSourceMapper.deleteExistedSourceById(Integer.parseInt(id));
        return data;
    }

    @Override
    public String findSpiderByName(String name){
        String data = spiderSourceMapper.findSpiderSourceByName(name);
        return data;
    }

    @Override
    public int updateSpider(SpiderSourceDTO spiderSource){
        SpiderSource spiderSourceModel = new SpiderSource();
        spiderSourceModel.setId(Integer.parseInt(spiderSource.getId()));
        spiderSourceModel.setName(spiderSource.getName());
        spiderSourceModel.setWebsite(spiderSource.getWebsite());
        spiderSourceModel.setCreateTime(spiderSource.getCreateTime());
        spiderSourceModel.setStatus(spiderSource.getStatus());
        spiderSourceModel.setSink(spiderSource.getSink());
        spiderSourceModel.setDescription(spiderSource.getDescription());
        spiderSourceModel.setMinute(spiderSource.getMinute());
        spiderSourceModel.setHour(spiderSource.getHour());
        spiderSourceModel.setFrequency(spiderSource.getFrequency());
        spiderSourceModel.setValue(spiderSource.getValue());
        int data = spiderSourceMapper.updateExistedSpiderSourceById(spiderSourceModel);
        return data;
    }

    @Override
    public SpiderSourceDTO findSpiderById(Integer id) {
        SpiderSource spiderSourceById = spiderSourceMapper.findSpiderSourceById(id);
        SpiderSourceDTO spiderSource = new SpiderSourceDTO();
        spiderSource.setId(String.valueOf(spiderSourceById.getId()));
        spiderSource.setSink(spiderSourceById.getSink());
        spiderSource.setWebsite(spiderSourceById.getWebsite());
        spiderSource.setStatus(String.valueOf(spiderSourceById.getStatus()));
        spiderSource.setCreateTime(spiderSourceById.getCreateTime());
        spiderSource.setDescription(spiderSourceById.getDescription());
        spiderSource.setName(spiderSourceById.getName());
        spiderSource.setMinute(spiderSourceById.getMinute());
        spiderSource.setHour(spiderSourceById.getHour());
        spiderSource.setFrequency(spiderSourceById.getFrequency());
        spiderSource.setValue(spiderSourceById.getValue());
        return spiderSource;
    }

    @Override
    public SpiderSourceDTO stopSpider(SpiderSourceDTO spiderSource) {
        if (spiderSource.getFrequency()!=null && spiderSource.getMinute()!=null && spiderSource.getHour()!= null){//停止定时
            spiderManager.stopSpiderTimingUsingShell(spiderSource.getName());
        }else{//停止普通
            spiderManager.stopSpiderUsingShell(spiderSource.getName());
        }
        boolean isRunningAfterStop = spiderManager.probeSpiderByName(spiderSource.getName());
        if (!isRunningAfterStop) {
            SpiderSource spiderSourceModel = new SpiderSource();
            spiderSourceModel.setId(Integer.parseInt(spiderSource.getId()));
            spiderSourceModel.setName(spiderSource.getName());
            spiderSourceModel.setWebsite(spiderSource.getWebsite());
            spiderSourceModel.setCreateTime(spiderSource.getCreateTime());
            spiderSourceModel.setStatus("2");
            spiderSourceModel.setSink(spiderSource.getSink());
            spiderSourceModel.setDescription(spiderSource.getDescription());
            spiderSourceModel.setMinute(spiderSource.getMinute());
            spiderSourceModel.setHour(spiderSource.getHour());
            spiderSourceModel.setFrequency(spiderSource.getFrequency());
            spiderSourceModel.setValue(spiderSource.getValue());
            boolean isUpdateSuccess = spiderSourceMapper.updateSpiderSourceStatus(spiderSourceModel);
            if (isUpdateSuccess) {
                // 更新状态成功，返回值中的status置为停止
                spiderSource.setStatus("2");
            }
        }else {
            spiderSource.setStatus("1");
        }
        return spiderSource;
    }

    @Override
    public SpiderSourceDTO pauseSpider(SpiderSourceDTO spiderSource){
        if (spiderSource.getStatus().equals("3") || spiderSource.getStatus().equals("2")) {
            // 如果已经处于暂停/停止状态，直接返回
            return spiderSource;
        }
        spiderManager.pauseSpiderUsingShell(spiderSource.getName());
        SpiderSource spiderSourceModel = new SpiderSource();
        spiderSourceModel.setId(Integer.parseInt(spiderSource.getId()));
        spiderSourceModel.setName(spiderSource.getName());
        spiderSourceModel.setWebsite(spiderSource.getWebsite());
        spiderSourceModel.setCreateTime(spiderSource.getCreateTime());
        spiderSourceModel.setStatus("3");
        spiderSourceModel.setSink(spiderSource.getSink());
        spiderSourceModel.setDescription(spiderSource.getDescription());
        spiderSourceModel.setMinute(spiderSource.getMinute());
        spiderSourceModel.setHour(spiderSource.getHour());
        spiderSourceModel.setFrequency(spiderSource.getFrequency());
        spiderSourceModel.setValue(spiderSource.getValue());
        boolean isUpdateSuccess = spiderSourceMapper.updateSpiderSourceStatus(spiderSourceModel);
        if (isUpdateSuccess) {
            // 更新状态成功，返回值中的status置为暂停
            spiderSource.setStatus("3");
        }
        return spiderSource;
    }

    @Override
    public SpiderSourceDTO submitNewSpiderSource(SpiderSourceDTO spiderSource) {
        spiderSource.setCreateTime(LocalDateTime.now());
        spiderSource.setStatus("2");
        spiderSource.setSink("kafka");
        SpiderSource spiderSourceModel = new SpiderSource();
        spiderSourceModel.setName(spiderSource.getName());
        spiderSourceModel.setWebsite(spiderSource.getWebsite());
        spiderSourceModel.setCreateTime(spiderSource.getCreateTime());
        spiderSourceModel.setStatus("2");
        spiderSourceModel.setSink(spiderSource.getSink());
        spiderSourceModel.setDescription(spiderSource.getDescription());
        spiderSourceModel.setMinute(spiderSource.getMinute());
        spiderSourceModel.setHour(spiderSource.getHour());
        spiderSourceModel.setFrequency(spiderSource.getFrequency());
        spiderSourceModel.setValue(spiderSource.getValue());
        Integer id = spiderSourceMapper.insertNewSpiderSource(spiderSourceModel);
        if (id != null) {
            spiderSource.setId(String.valueOf(id));
            return spiderSource;
        }
        spiderSource.setId("-1");
        return spiderSource;
    }

    @Override
    public List<SpiderSourceDTO> fetchAllSpiderSources() {
        List<SpiderSource> allSpiderSources = spiderSourceMapper.findAllSpiderSource();
        List<SpiderSourceDTO> result = new ArrayList<>();
        for (SpiderSource sourceModel : allSpiderSources) {
            SpiderSourceDTO spiderSource = new SpiderSourceDTO();
            spiderSource.setId(String.valueOf(sourceModel.getId()));
            spiderSource.setName(sourceModel.getName());
            spiderSource.setDescription(sourceModel.getDescription());
            spiderSource.setWebsite(sourceModel.getWebsite());
            spiderSource.setStatus(String.valueOf(sourceModel.getStatus()));
            spiderSource.setSink(sourceModel.getSink());
            spiderSource.setCreateTime(sourceModel.getCreateTime());
            spiderSource.setMinute(sourceModel.getMinute());
            spiderSource.setHour(sourceModel.getHour());
            spiderSource.setFrequency(sourceModel.getFrequency());
            spiderSource.setValue(sourceModel.getValue());
            result.add(spiderSource);
        }
        return result;
    }
    //end 爬虫采集

    //start  公司数据采集
        // MYSQL
    @Override
    public DataXDTO submitDataX(DataXDTO dataXDTO){

        //TO DO 每天同步一次
        Thread thread = new Thread(() -> {
            ImportDatabaseByDatax importDatabase = new ImportDatabaseByDatax(
                    dataXDTO.getId(),
                    dataXDTO.getDbName(),
                    dataXDTO.getsIp(),
                    dataXDTO.getsPort(),
                    dataXDTO.getSourceUsername(),
                    dataXDTO.getSourcePassword(),
                    dataXDTO.getpIp(),
                    dataXDTO.getpPort(),
                    dataXDTO.getPlatUserName(),
                    dataXDTO.getPlatPassword(),
                    DATAX_JOB_PATH);
        });
        thread.start();
        return dataXDTO;
    }


        //pSpace
    @Override
    public PSpaceDTO submitPSpace(PSpaceDTO pSpace){
        ExportPspaceData exportPspaceData = new ExportPspaceData();
        String dataType = pSpace.getDataType();
        int status;
        if(dataType.equals("point")){
            status = exportPspaceData.exportPointData(
                    pSpace.getUrl(),
                    pSpace.getPort(),
                    pSpace.getUser(),
                    pSpace.getSqlPassword(),
                    pSpace.getSqlIP(),
                    pSpace.getSqlPort(),
                    pSpace.getSqlUser(),
                    pSpace.getSqlPassword());
        }else if(dataType.equals("realDynamic")){
            status = exportPspaceData.exportRealDynamicData(
                    pSpace.getUrl(),
                    pSpace.getPort(),
                    pSpace.getUser(),
                    pSpace.getSqlPassword(),
                    pSpace.getSqlIP(),
                    pSpace.getSqlPort(),
                    pSpace.getSqlUser(),
                    pSpace.getSqlPassword());
        }else if(dataType.equals("history")){
            // TODO 每天自动调用 TimerTask
            status = exportPspaceData.exportHistoryData(
                    pSpace.getUrl(),
                    pSpace.getPort(),
                    pSpace.getUser(),
                    pSpace.getSqlPassword(),
                    pSpace.getStart(),
                    pSpace.getEnd(),
                    pSpace.getSqlIP(),
                    pSpace.getSqlPort(),
                    pSpace.getSqlUser(),
                    pSpace.getSqlPassword());
        }else if (dataType.equals("realAlarm")){
            status = exportPspaceData.exportRealAlarmData(
                    pSpace.getUrl(),
                    pSpace.getPort(),
                    pSpace.getUser(),
                    pSpace.getSqlPassword(),
                    pSpace.getSqlIP(),
                    pSpace.getSqlPort(),
                    pSpace.getSqlUser(),
                    pSpace.getSqlPassword()
            );
        }else if (dataType.equals("hisAlarm")){
            // TODO 每天自动调用 TimerTask
            status = exportPspaceData.exportHisAlarmData(
                    pSpace.getUrl(),
                    pSpace.getPort(),
                    pSpace.getUser(),
                    pSpace.getSqlPassword(),
                    pSpace.getStart(),
                    pSpace.getEnd(),
                    pSpace.getSqlIP(),
                    pSpace.getSqlPort(),
                    pSpace.getSqlUser(),
                    pSpace.getSqlPassword()
            );
        }
        return pSpace;
    }
    //end 公司数据采集

    @Override
    public MySQLTaskDTO findMySQLById(Integer id) {
        MySQLTask mySQLTaskById = mySQLTaskMapper.findMySQLTaskById(id);
        MySQLTaskDTO foundTask = new MySQLTaskDTO();
        foundTask.setId(String.valueOf(mySQLTaskById.getId()));
        foundTask.setDescription(mySQLTaskById.getDescription());
        foundTask.setName(mySQLTaskById.getName());
        foundTask.setHost(mySQLTaskById.getHost());
        foundTask.setPort(mySQLTaskById.getPort());
        foundTask.setStatus(String.valueOf(mySQLTaskById.getStatus()));
        foundTask.setTargetTable(mySQLTaskById.getTargetTable());
        foundTask.setSchemaName(mySQLTaskById.getSchemaName());
        foundTask.setMode(String.valueOf(mySQLTaskById.getMode()));
        foundTask.setUsername(mySQLTaskById.getUsername());
        foundTask.setPassword(mySQLTaskById.getPassword());
        return foundTask;
    }

    @Override
    public MySQLTaskDTO launchMySQLTask(MySQLTaskDTO mySQLTask) {
        // TODO 开启采集
        if (mySQLTask.getMode().equals("1")) {
            // TODO 流式采集
        } else {
            // TODO 批量采集
        }
        // 更新数据库状态，更新状态和pid
        MySQLTask mySQLTaskModel = new MySQLTask();
        mySQLTaskModel.setId(Integer.parseInt(mySQLTask.getId()));
        mySQLTaskModel.setName(mySQLTask.getName());
        mySQLTaskModel.setDescription(mySQLTask.getDescription());
        mySQLTaskModel.setStatus(1);
        mySQLTaskModel.setMode(Integer.parseInt(mySQLTask.getMode()));
        mySQLTaskModel.setHost(mySQLTask.getHost());
        mySQLTaskModel.setPort(mySQLTask.getPort());
        mySQLTaskModel.setSchemaName(mySQLTask.getSchemaName());
        mySQLTaskModel.setTargetTable(mySQLTask.getTargetTable());
        mySQLTaskModel.setUsername(mySQLTask.getUsername());
        mySQLTaskModel.setPassword(mySQLTask.getPassword());
        boolean isUpdateSuccess = mySQLTaskMapper.updateMySQLTaskStatus(mySQLTaskModel);
        if (isUpdateSuccess) {
            // 更新状态成功，返回值中的status置为运行中
            mySQLTask.setStatus("1");
        }
        return mySQLTask;
    }

    @Override
    public MySQLTaskDTO stopMySQLTask(MySQLTaskDTO mySQLTask) {
        // 更新数据库状态，更新状态和pid
        MySQLTask mySQLTaskModel = new MySQLTask();
        mySQLTaskModel.setId(Integer.parseInt(mySQLTask.getId()));
        mySQLTaskModel.setName(mySQLTask.getName());
        mySQLTaskModel.setDescription(mySQLTask.getDescription());
        mySQLTaskModel.setStatus(2);
        mySQLTaskModel.setMode(Integer.parseInt(mySQLTask.getMode()));
        mySQLTaskModel.setHost(mySQLTask.getHost());
        mySQLTaskModel.setPort(mySQLTask.getPort());
        mySQLTaskModel.setSchemaName(mySQLTask.getSchemaName());
        mySQLTaskModel.setTargetTable(mySQLTask.getTargetTable());
        mySQLTaskModel.setUsername(mySQLTask.getUsername());
        mySQLTaskModel.setPassword(mySQLTask.getPassword());
        boolean isUpdateSuccess = mySQLTaskMapper.updateMySQLTaskStatus(mySQLTaskModel);
        if (isUpdateSuccess) {
            // 更新状态成功，返回值中的status置为运行中
            mySQLTask.setStatus("1");
        }
        return mySQLTask;
    }

    @Override
    public MySQLTaskDTO submitNewMySQLTask(MySQLTaskDTO mySQLTask) {
        MySQLTask mySQLTaskModel = new MySQLTask();
        mySQLTaskModel.setName(mySQLTask.getName());
        mySQLTaskModel.setDescription(mySQLTask.getDescription());
        mySQLTaskModel.setCreateTime(mySQLTask.getCreateTime());
        mySQLTaskModel.setStatus(2);
        mySQLTaskModel.setMode(Integer.parseInt(mySQLTask.getMode()));
        mySQLTaskModel.setHost(mySQLTask.getHost());
        mySQLTaskModel.setPort(mySQLTask.getPort());
        mySQLTaskModel.setSchemaName(mySQLTask.getSchemaName());
        mySQLTaskModel.setTargetTable(mySQLTask.getTargetTable());
        mySQLTaskModel.setUsername(mySQLTask.getUsername());
        mySQLTaskModel.setPassword(mySQLTask.getPassword());
        Integer id = mySQLTaskMapper.insertMySQLTask(mySQLTaskModel);
        if (id != null) {
            mySQLTask.setId(String.valueOf(id));
            return mySQLTask;
        }
        mySQLTask.setId("-1");
        return mySQLTask;
    }

    @Override
    public List<MySQLTaskDTO> fetchAllMySQLTasks() {
        List<MySQLTask> allMySQLTasks = mySQLTaskMapper.findAllMySQLTask();
        List<MySQLTaskDTO> result = new ArrayList<>();
        for (MySQLTask taskModel : allMySQLTasks) {
            MySQLTaskDTO mySQLTaskDTO = new MySQLTaskDTO();
            mySQLTaskDTO.setId(String.valueOf(taskModel.getId()));
            mySQLTaskDTO.setName(taskModel.getName());
            mySQLTaskDTO.setDescription(taskModel.getDescription());
            mySQLTaskDTO.setStatus(String.valueOf(taskModel.getStatus()));
            mySQLTaskDTO.setMode(String.valueOf(taskModel.getMode()));
            mySQLTaskDTO.setHost(String.valueOf(taskModel.getHost()));
            mySQLTaskDTO.setPort(taskModel.getPort());
            mySQLTaskDTO.setSchemaName(taskModel.getSchemaName());
            mySQLTaskDTO.setTargetTable(taskModel.getTargetTable());
            mySQLTaskDTO.setUsername(taskModel.getUsername());
            mySQLTaskDTO.setPassword(taskModel.getPassword());
            mySQLTaskDTO.setCreateTime(taskModel.getCreateTime());
            result.add(mySQLTaskDTO);
        }
        return result;
    }

    @Override
    public boolean checkMySQLConnectionSuccess(MySQLTaskDTO mySQLTaskDTO) {
        String url = "jdbc:mysql://" + mySQLTaskDTO.getHost() + ":" + mySQLTaskDTO.getPort(); //pointing to no database.
        String username = mySQLTaskDTO.getUsername();
        String password = mySQLTaskDTO.getPassword();
        logger.debug(url);
        logger.debug(username);
        logger.debug(password);
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (connection == null) {
                logger.error("Cannot connect the server [" + mySQLTaskDTO.getHost() + "]");
                return false;
            }

            boolean valid = connection.isValid(10);
            if (!valid) {
                logger.error("Cannot connect the server [" + mySQLTaskDTO.getHost() + "]");
            }
            connection.close();
            return valid;
        } catch (SQLException e) {
            logger.error("Cannot connect the server [" + mySQLTaskDTO.getHost() + "]", e);
            return false;
        }
    }
}
