package cn.edu.xidian.bigdataplatform.mysqlbackup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MysqlBackupList {
    /**
     * 获取路径下的所有文件/文件夹
     * 
     * @param directoryPath  需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     */
    
    String directoryPath = "/opt/mysqlBackup/" + "前端传入的用户";

    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (isAddDirectory) {
                    // list.add(file.getAbsolutePath()); //获取文件路径 添加到list集合
                    list.add(file.getName());
                }
                list.addAll(getAllFile(file.getAbsolutePath(), isAddDirectory));
            } else {
                // list.add(file.getAbsolutePath());//获取文件路径 添加到list集合
                list.add(file.getName()); // 获取文件名 添加到list集合
            }
        }
        return list;
    }

}