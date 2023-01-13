package cn.edu.xidian.bigdataplatform.mybatis.entity;

import org.apache.commons.lang3.StringUtils;

//        {
//            "driver" : "com.mysql.cj.jdbc.Driver",
//            "password" : "zlx1754wanc",
//            "plugin_name" : "jdbc",
//            "result_table_name" : "freight",
//            "table" : "freight_source",
//            "url" : "jdbc:mysql://localhost:3306/wutong",
//            "user" : "root"
//        }
public class JDBCSource extends Source {
    private String driver;
    private String password;
    private String pluginName;
    private String table;
    private String url;
    private String user;
    private String resultTableName;

    public JDBCSource(String driver, String password, String table, String url, String user, String resultTableName) {
        this.driver = driver;
        this.password = password;
        this.resultTableName = resultTableName;
        this.pluginName = "source_jdbc";
        this.table = table;
        this.url = url;
        this.user = user;
        this.sourceConfigName = "jdbc";
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setResultTableName(String resultTableName) {
        this.resultTableName = resultTableName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"driver\":\"")
                .append(driver).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"pluginName\":\"")
                .append(pluginName).append('\"');
        sb.append(",\"table\":\"")
                .append(table).append('\"');
        sb.append(",\"url\":\"")
                .append(url).append('\"');
        sb.append(",\"user\":\"")
                .append(user).append('\"');
        sb.append(",\"resultTableName\":\"")
                .append(resultTableName).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String toConfig(int indent) {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.repeat("\t", indent - 1));
        sb.append(this.sourceConfigName);
        sb.append(" {\n");
        sb.append(StringUtils.repeat("\t", indent)).append("driver").append(" = ").append(StringUtils.wrap(driver, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent)).append("password").append(" = ").append(StringUtils.wrap(password, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent)).append("table").append(" = ").append(StringUtils.wrap(table, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent)).append("url").append(" = ").append(StringUtils.wrap(url, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent)).append("result_table_name").append(" = ").append(StringUtils.wrap(resultTableName, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent)).append("user").append(" = ").append(StringUtils.wrap(user, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent - 1));
        sb.append("}\n");
        return sb.toString();
    }
}
