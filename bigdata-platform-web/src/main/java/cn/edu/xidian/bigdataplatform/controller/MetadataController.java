package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.CleansingService;
import cn.edu.xidian.bigdataplatform.DataSourceService;
import cn.edu.xidian.bigdataplatform.IngestionService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.base.ResultCode;
import cn.edu.xidian.bigdataplatform.bean.*;
import cn.edu.xidian.bigdataplatform.bean.MetaDataControl;
import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.MysqlSourceInfo;
import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.TableData;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import org.apache.lucene.index.ReaderSlice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import schemacrawler.schema.DatabaseInfo;
import schemacrawler.schema.DatabaseProperty;
import schemacrawler.schema.Property;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-21
 * @description:
 */
@RestController
public class MetadataController {
    private static Logger logger = LoggerFactory.getLogger(MetadataController.class);

    @Autowired
    private IngestionService ingestionService;

    @Autowired
    private CleansingService cleansingService;

    @Autowired
    DataSourceService dataSourceService;

    @GetMapping("/api/getMetadata")
    public Result getMetadata() {
        List<MySQLTaskDTO> mysqlTask = ingestionService.fetchAllMySQLTasks();
        List<DatabaseInfoBean> result = new ArrayList<>();
        for (MySQLTaskDTO task : mysqlTask) {
            DatabaseInfoBean databaseInfoBean = cleansingService.fetchSchemaMetadata(task.getSchemaName());
            result.add(databaseInfoBean);
        }
        return Result.success(result);
    }

    @GetMapping("/api/getTableMetadata")
    public Result getTableMetadata(@RequestParam(name="dataSourceId") String dataSourceId, @RequestParam(name = "tableName") String tableName) {
        MySQLSource mySQLSource = dataSourceService.findMySQLSourceById(dataSourceId);
        try {
            List<MetaDataItem> metaDataItemList = MetaDataControl.fetchMetaData(mySQLSource, tableName);
            return Result.success(metaDataItemList);
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.failed(ResultCode.FAILED);
        }
    }

    @PostMapping("/api/alterTableMetadata")
    public Result alterTableMetadata(@RequestBody MetaDataAlterRequest metaDataAlterRequest) {
        String dataSourceId = metaDataAlterRequest.getDataSourceId();
        String tableName = metaDataAlterRequest.getTableName();
        String columnName = metaDataAlterRequest.getColumnName();
        MetaDataItem metaDataItem = metaDataAlterRequest.getMetaDataItem();
        MySQLSource mySQLSource = dataSourceService.findMySQLSourceById(dataSourceId);
        try {
            MetaDataControl.alterColumn(mySQLSource, tableName, columnName, metaDataItem);
            return Result.success(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return  Result.failed(e.getMessage());
        }
    }
}
