package cn.edu.xidian.bigdataplatform.mysqlbackup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlBackup {
    private DataSource datasource;
    private ObjectMapper mapper = new ObjectMapper();
    public static void main(String args[]) throws IOException, SQLException {
        InputStream is = MysqlBackup.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
//      MysqlBackup.export(properties);
//      MysqlBackup.importSql(properties);
    }

    public static void export(Properties properties) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String command = getExportCommand(properties);
        runtime.exec(command);
    }


    /**
     * 利用属性文件提供的配置来拼装命令语句
     * 在拼装命令语句的时候有一点是需要注意的：一般我们在命令窗口直接使用命令来
     * 进行导出的时候可以简单使用“>”来表示导出到什么地方，即mysqldump -uusername -ppassword databaseName >
     * exportPath，
     * 但在Java中这样写是不行的，它需要你用-r明确的指出导出到什么地方，如：
     * mysqldump -uusername -ppassword databaseName -r exportPath。
     */
    private static String getExportCommand(Properties properties) {
        StringBuffer command = new StringBuffer();
        String username = properties.getProperty("jdbc.username");
        // 用户名 由前端传递
        String password = properties.getProperty("jdbc.password");
        // 用户密码 由前端传递
        String exportDatabaseName = properties.getProperty("jdbc.exportDatabaseName");// 需要导出的数据库名 由前端传递
        String host = properties.getProperty("jdbc.host");
        // 从哪个主机导出数据库 由配置文件指定hadoop000
        String port = properties.getProperty("jdbc.port");
        // 使用的端口号 由配置文件指定
        String exportPath = properties.getProperty("jdbc.exportPath");
        String exportPathFull = exportPath + "";
        // 导出路径，由配置文件指定 + 前端传递 拼接

        // 注意哪些地方要空格，哪些不要空格
        command.append("mysqldump -u").append(username).append(" -p").append(password)// 密码是用的小p，而端口是用的大P。
                .append(" -h ").append(host).append(" -P").append(port).append(" ").append(exportDatabaseName)
                .append(" -r ").append(exportPathFull);

        return command.toString();
    }

}