package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.DataSourceService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.base.ResultCode;
import cn.edu.xidian.bigdataplatform.bean.ShowFunc;
import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.TableData;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Vector;

@RestController
public class DataBaseInfoController {
    @Autowired
    DataSourceService dataSourceService;
    @GetMapping("/api/showDataBaseTables")
    public Result showDataBaseTables(@RequestParam(name="dataSourceId") String dataSourceId) {
        MySQLSource mySQLSource = dataSourceService.findMySQLSourceById(dataSourceId);
        try {
            Vector<String> tables= ShowFunc.showTables(mySQLSource);
            return Result.success(tables);
        } catch (Exception e) {
            return Result.failed(ResultCode.FAILED);
        }
    }
}
