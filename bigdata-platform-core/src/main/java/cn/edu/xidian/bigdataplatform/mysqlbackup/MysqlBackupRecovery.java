package cn.edu.xidian.bigdataplatform.mysqlbackup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class MysqlBackupRecovery {
    public static void main(String args[]) throws IOException {
        InputStream is = MysqlBackupRecovery.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        // MysqlBackup.export(properties);
        MysqlBackupRecovery.importSql(properties);
    }
    /**
     * 根据属性文件的配置把指定位置的指定文件内容导入到指定的数据库中
     * 在命令窗口进行mysql的数据库导入一般分三步走：
     * 第一步是登到到mysql； mysql -uusername -ppassword -hhost -Pport
     * -DdatabaseName;如果在登录的时候指定了数据库名则会
     * 直接转向该数据库，这样就可以跳过第二步，直接第三步；
     * 第二步是切换到导入的目标数据库；use importDatabaseName；
     * 第三步是开始从目标文件导入数据到目标数据库；source importPath；
     */
    public static void importSql(Properties properties) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        // 因为在命令窗口进行mysql数据库的导入一般分三步走，所以所执行的命令将以字符串数组的形式出现
        String[] cmdarray = getImportCommand(properties);
        // 根据属性文件的配置获取数据库导入所需的命令，组成一个数组
        Process process = runtime.exec(cmdarray[0]);
        // 执行了第一条命令以后已经登录到mysql了，所以之后就是利用mysql的命令窗口进程执行后面的代码
        OutputStream os = process.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        // 命令1和命令2要放在一起执行
        writer.write(cmdarray[1] + "\r\n" + cmdarray[2]);
        writer.flush();
        writer.close();
        os.close();
    }
    /**
     * 根据属性文件的配置，分三步走获取从目标文件导入数据到目标数据库所需的命令
     * 如果在登录的时候指定了数据库名则会
     * 直接转向该数据库，这样就可以跳过第二步，直接第三步；
     */
    private static String[] getImportCommand(Properties properties) {
        // StringBuffer command = new StringBuffer();
        String username = properties.getProperty("jdbc.username");
        // 用户名 前端传递
        String password = properties.getProperty("jdbc.password");
        // 密码 前端传递
        String host = properties.getProperty("jdbc.host");
        // 导入的目标数据库所在的主机 配置文件指定
        String port = properties.getProperty("jdbc.port");
        // 使用的端口号 配置文件指定
        String importDatabaseName = properties.getProperty("jdbc.importDatabaseName");
        // 导入的目标数据库的名称 前端传递
        String importPath = properties.getProperty("jdbc.importPath");
        String importPathFull = importPath + "";
        // 导入的目标文件所在的位置 由配置文件指定 + 前端传递  拼接

        // 第一步，获取登录命令语句 注意哪些地方要空格，哪些不要空格
        String loginCommand = new StringBuffer().append("mysql -u").append(username).append(" -p").append(password)
                .append(" -h ").append(host)
                .append(" -P").append(port).toString();
        // 第二步，获取切换数据库到目标数据库的命令语句
        String switchCommand = new StringBuffer("use ").append(importDatabaseName).toString();
        // 第三步，获取导入的命令语句
        String importCommand = new StringBuffer("source ").append(importPathFull).toString();
        // 需要返回的命令语句数组
        String[] commands = new String[] { loginCommand, switchCommand, importCommand };
        return commands;
    }

}
