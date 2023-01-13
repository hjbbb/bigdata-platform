package cn.edu.xidian.bigdataplatform.mybatis.entity.usersource;

import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.DBColumn;
import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.DBTable;
import cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt.MysqlSourceInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.tools.utility.SchemaCrawlerUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-08
 * @description:
 */
public class MySQLSource {
    private int id;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String host;
    private int port;
    private String schemaName;
    private String username;
    private String password;


    public MySQLSource() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection createConnection() throws SQLException {
        final String url = "jdbc:mysql://" + host + ":" + port + "/" + schemaName;
        return DriverManager.getConnection(url, username, password);
    }

    public MysqlSourceInfo fetchDataSourceInfo() throws SQLException {
        try (Connection dbConnection = createConnection()) {
            final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions();
            final Catalog catalog = SchemaCrawlerUtility.getCatalog(dbConnection, options);
            Collection<Schema> schemas = catalog.getSchemas();
            for (final Schema schema : catalog.getSchemas()) {
                if (schema.getFullName().equals(schemaName)) {
                    MysqlSourceInfo sourceInfo = new MysqlSourceInfo();
                    sourceInfo.setName(schemaName);
                    List<DBTable> dbTables = new ArrayList<DBTable>();
                    for (final Table table : catalog.getTables(schema)) {
                        DBTable dbTable = new DBTable();
                        dbTable.setName(table.getName());
                        List<DBColumn> dbColumns = new ArrayList<DBColumn>();
                        for (final Column column : table.getColumns()) {
                            DBColumn dbColumn = new DBColumn();
                            dbColumn.setName(column.getName());
                            dbColumn.setType(column.getColumnDataType().getDatabaseSpecificTypeName());
                            dbColumns.add(dbColumn);
                        }
                        dbTable.setColumns(dbColumns);
                        dbTables.add(dbTable);
                    }
                    sourceInfo.setTables(dbTables);
                    dbConnection.close();
                    return sourceInfo;
                }
            }
            dbConnection.close();
            return null;
        }
    }
}
