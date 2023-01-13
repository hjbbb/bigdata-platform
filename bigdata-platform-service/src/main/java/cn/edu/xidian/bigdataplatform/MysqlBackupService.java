package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.mybatis.entity.mysqlbackup.BackupItem;

import java.util.List;

public interface MysqlBackupService {
    List<BackupItem> getAllBackupItems(String uuid);
    BackupItem newBackupItem(BackupItem backupItem);
    BackupItem getItemDetail(int itemId);
//    List<String> showDatabases();
    boolean deleteItemById(int itemId);

    void recovery(int itemId);
}
