package cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt;

import java.util.List;

public class MysqlSourceInfo {
    private String name;
    private List<DBTable> tables;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DBTable> getTables() {
        return tables;
    }

    public void setTables(List<DBTable> tables) {
        this.tables = tables;
    }
}
