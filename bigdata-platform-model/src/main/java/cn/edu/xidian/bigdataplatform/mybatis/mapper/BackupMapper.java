package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.mysqlbackup.BackupItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BackupMapper {
    List<BackupItem> getAllBackupItems(String uuid);
    int insertNewBackupItem(BackupItem backupItem);
    boolean updateBackupItem(BackupItem backupItem);
    BackupItem getItemDetail(int id);
    List<String> showDatabases();

    boolean deleteItemById(int id);
}
