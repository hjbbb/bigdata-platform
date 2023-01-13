package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.DataSourceService;
import cn.edu.xidian.bigdataplatform.UserService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.base.ResultCode;
import cn.edu.xidian.bigdataplatform.bean.EncTask;
import cn.edu.xidian.bigdataplatform.bean.ShowFunc;
import cn.edu.xidian.bigdataplatform.bean.keyCreate;
import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.EncKeyPair;
import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.EncPublicKey;
import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.TableData;
import cn.edu.xidian.bigdataplatform.mybatis.entity.user.User;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Vector;

@RestController
public class DataEncAndDecController {
    @Autowired
    UserService userService;
    @Autowired
    DataSourceService dataSourceService;

    @PostMapping("/api/submitEncTask")
    public Result submitEncTask(@RequestBody EncTask encTask) {
        User user  = userService.findUserByUsername(encTask.userName);
        String pk = user.getPk();
        System.out.println("pk: " + pk);
        if(pk == null) {
            return Result.failed(ResultCode.PUBLIC_KEY_ERROR);
        }
        MySQLSource mySQLSource = dataSourceService.findMySQLSourceById(encTask.dataSourceId);
        int status = encTask.exec(pk, mySQLSource);
        switch (status){
            case 2://status=2:sql error
                return Result.failed(ResultCode.SQL_ERROR);
            case 3://status=3:database name not exist
                return Result.failed(ResultCode.DATABASE_CONNOT_CONNECT);
            case 4://status=4:encField name not exist
                return Result.failed(ResultCode.FIELD_NOT_EXIST);
            default:
                return Result.success("请求成功，加密完成");
        }
    }

    @PostMapping("/api/keyPairCreate")
    public Result keyPairCreate(@RequestBody keyCreate keyCreate) {
        String username = keyCreate.getUsername();
        EncKeyPair keyPair = keyCreate.create();
        int line = userService.updateUserPk(username, keyPair.getPublicKey());
        if(keyPair.getPrivateKey()==null || line != 1)
            return Result.failed(ResultCode.FAILED);
//		System.out.println("sk: "+sk);

        return Result.success(keyPair);
    }

    @GetMapping("/api/getUserPublicKey")
    public Result getUserPublicKey(@RequestParam(name = "username") String username) {
        User user = userService.findUserByUsername(username);
        if(user == null) {
            return Result.failed(ResultCode.FAILED);
        }
        if(user.getPk() == null) {
            return Result.success(null);
        } else {
            EncPublicKey pk = new EncPublicKey(user.getPk(), user.getPkCreateTime());
            return Result.success(pk);
        }
    }
    @DeleteMapping("/api/deleteUserPublicKey")
    public Result deleteUserPublicKey(@RequestParam(name = "username") String username) {
        int line = userService.deleteUserPk(username);
        if(line != 1) {
            return Result.failed(ResultCode.FAILED);
        } else {
            return Result.success(true);
        }
    }

    @GetMapping("/api/getTableData")
    public Result getTableData(@RequestParam(name="dataSourceId") String dataSourceId, @RequestParam(name = "tableName") String tableName,
                               @RequestParam(name="pageNum") int pageNum, @RequestParam(name="pageSize") int pageSize) {
        MySQLSource mySQLSource = dataSourceService.findMySQLSourceById(dataSourceId);
        TableData tableData= ShowFunc.showSqlData(mySQLSource, tableName, null, pageNum, pageSize);
        if(tableData == null) {
            return Result.failed(ResultCode.FAILED);
        } else {
            return Result.success(tableData);
        }
    }
}