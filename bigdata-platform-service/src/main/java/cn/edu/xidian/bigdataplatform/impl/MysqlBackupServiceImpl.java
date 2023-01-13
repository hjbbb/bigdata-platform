package cn.edu.xidian.bigdataplatform.impl;

import cn.edu.xidian.bigdataplatform.MysqlBackupService;
import cn.edu.xidian.bigdataplatform.mybatis.entity.mysqlbackup.BackupItem;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.BackupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MysqlBackupServiceImpl implements MysqlBackupService {
    @Autowired
    private BackupMapper backupMapper;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public List<BackupItem> getAllBackupItems(String uuid) {
        return backupMapper.getAllBackupItems(uuid);
    }

    @Override
    public BackupItem newBackupItem(BackupItem backupItem) {
        int line = backupMapper.insertNewBackupItem(backupItem);
        if (line != 1) {
            return null;
        }
        backup(backupItem);
        return backupItem;
    }

    public BackupItem getItemDetail(int id) {
        return backupMapper.getItemDetail(id);
    }

//    public List<String> showDatabases() {
//        return backupMapper.showDatabases();
//    }

    @Override
    public boolean deleteItemById(int itemId) {
        return backupMapper.deleteItemById(itemId);
    }

    @Override
    public void recovery(int id) {
        BackupItem item = backupMapper.getItemDetail(id);
        try {
            recoveryItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backup(BackupItem backupItem) {
        StringBuffer buf = new StringBuffer();
        String uuid = backupItem.getUuid();
        String username = backupItem.getUsername();
        String password = backupItem.getPassword();
        String dbName = backupItem.getDbName();
        String host = backupItem.getHost();
        String port = String.valueOf(backupItem.getPort());
        String exportPath = backupItem.getStoragePlace();
        String type = backupItem.getType();
        String exportPathFull =  exportPath + backupItem.getId() + "_" + backupItem.getName();


        // 注意哪些地方要空格，哪些不要空格
        buf.append("mysqldump -u").append(username).append(" -p").append(password)// 密码是用的小p，而端口是用的大P。
                .append(" -h ").append(host).append(" -P").append(port).append(" --databases ").append(dbName)
                .append(" -r ").append(exportPathFull);

        String command = buf.toString();


        if (backupItem.getType().equals("periodic")) {
            try {
                ProcessBuilder builder = new ProcessBuilder("sh", "-c", "crontab -l > /tmp/crontab");
                Process p = builder.start();
                p.waitFor();
                FileWriter fw = new FileWriter("/tmp/crontab", true);
                StringBuffer strBuf = new StringBuffer();
                String periodUnit = backupItem.getPeriodUnit();
                int periodFreq = backupItem.getPeriodFreq();
                switch (periodUnit) {
                    case "minute": {
                        strBuf.append("*/").append(periodFreq).append(" * * * * ").append(command).append(" > /dev/null");
                        break;
                    }
                    case "hour": {
                        strBuf.append("* */").append(periodFreq).append(" * * * ").append(command).append(" > /dev/null");
                        break;
                    }
                    case "day": {
                        strBuf.append("* * */").append(periodFreq).append(" * * ").append(command).append(" > /dev/null");
                        break;
                    }
                    case "month": {
                        strBuf.append("* * * */").append(periodFreq).append(" * ").append(command).append(" > /dev/null");
                        break;
                    }
                }
                fw.write(strBuf.toString());
                fw.write("\n");
                fw.close();
                builder = new ProcessBuilder("sh", "-c", "crontab /tmp/crontab");
                p = builder.start();
                p.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            ProcessBuilder builder = new ProcessBuilder("sh", "-c", command);
            Process p = builder.start();
            simpMessagingTemplate.convertAndSend("/mysql-backup-transport/" + uuid, new TaskMessage(backupItem.getId(), "backup_task",backupItem.getStatus(), backupItem.getMessage()));
            p.waitFor();
            InputStream is = p.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            Vector<String> res = new Vector<>();
            String line;
            String pattern = "error: \\d+: (.*)";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(line);
                if (m.find()) {
                    backupItem.setStatus("错误");
                    backupItem.setMessage(m.group(1));
                } else {
                    backupItem.setStatus("完成");
                }
            }
            backupMapper.updateBackupItem(backupItem);

        } catch (Exception e) {
            e.printStackTrace();
        }
        simpMessagingTemplate.convertAndSend("/mysql-backup-transport/" + uuid, new TaskMessage(backupItem.getId(), "backup_task",backupItem.getStatus(), backupItem.getMessage()));
    }

    private void recoveryItem(BackupItem backupItem) throws IOException {
        if(backupItem.getStatus().equals("错误")) {
            return;
        }
        String uuid = backupItem.getUuid();
        String username = backupItem.getUsername();
        String password = backupItem.getPassword();
        String host = backupItem.getHost();
        String port = String.valueOf(backupItem.getPort());
        String importPath = backupItem.getStoragePlace();
        String importPathFull =  importPath + backupItem.getId() + "_" + backupItem.getName();
        String loginCommand = new StringBuffer().append("mysql -u").append(username).append(" -p").append(password)
                .append(" -h ").append(host)
                .append(" -P").append(port).toString();
        // 获取导入的命令语句
        String importCommand = new StringBuffer("source ").append(importPathFull).toString();


        ProcessBuilder builder = new ProcessBuilder("sh", "-c", loginCommand);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        // 执行了第一条命令以后已经登录到mysql了，所以之后就是利用mysql的命令窗口进程执行后面的代码
        OutputStream os = process.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);

        writer.write(importCommand);
        writer.flush();
        writer.close();
        os.close();
        simpMessagingTemplate.convertAndSend("/mysql-backup-transport/" + uuid, new TaskMessage(backupItem.getId(), "recovery_task","done", "recovery done"));
        try {
            process.waitFor();
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}

class TaskMessage {
    private int itemId;
    private String type;
    private String status;
    private String message;


    public TaskMessage() {
    }

    public TaskMessage(int itemId, String type, String status, String message) {
        this.itemId = itemId;
        this.type = type;
        this.status = status;
        this.message = message;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
