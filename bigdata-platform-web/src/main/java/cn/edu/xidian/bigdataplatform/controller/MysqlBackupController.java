package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.MysqlBackupService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.mybatis.entity.mysqlbackup.BackupItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class MysqlBackupController {
    @Value("${backup.storagePlace}")
    private String storagePlace;
    private static final List<String> backlist = Arrays.asList("mysql", "sys", "performance_schema", "information_schema");
    private static Logger logger = LoggerFactory.getLogger(MysqlBackupController.class);

    @Autowired
    public MysqlBackupService mysqlBackupService;

    @GetMapping("/api/security/mysql-backup/backup-items")
    public Result getAllBackupItems(@RequestParam(name="uuid") String uuid) {
        List<BackupItem> items = mysqlBackupService.getAllBackupItems(uuid);
        return Result.success(items);
    }

    @PostMapping("/api/security/mysql-backup/backup-items")
    public Result newBackupItems(@RequestBody BackupItem backupItem) {
        backupItem.setDate(LocalDateTime.now());
        backupItem.setStoragePlace(storagePlace);
        backupItem.setStatus("等待中");
        BackupItem ret = mysqlBackupService.newBackupItem(backupItem);
        return Result.success(ret);
    }
    @GetMapping("/api/security/mysql-backup/backup-items/{item-id}")
    public Result getBackupItemsDetail(@PathVariable(value="item-id") int itemId) {
        BackupItem item = mysqlBackupService.getItemDetail(itemId);
        return Result.success(item);
    }

    @DeleteMapping("/api/security/mysql-backup/backup-items/{item-id}")
    public Result deleteBackupItem(@PathVariable(value="item-id") int itemId) {
        boolean ret = mysqlBackupService.deleteItemById(itemId);
        return Result.success(ret);
    }

    @GetMapping("/api/security/mysql-backup/recovery/{item-id}")
    public Result recoveryBackupItem(@PathVariable(value="item-id") int itemId) {
        mysqlBackupService.recovery(itemId);
        return Result.success("");
    }

//    前端暂时没有用到该接口
//    @GetMapping("/api/security/mysql-backup/show-databases")
//    public Result showDatabases() {
//        List<String> list = mysqlBackupService.showDatabases();
//        list.removeAll(backlist);
//        return Result.success(list);
//    }

//    @MessageMapping("/mysql-backup-transport")
//    @SendToUser("/user")
//    public String greeting(String message) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        return new String("Hello, " + "!");
//
//    }
}
