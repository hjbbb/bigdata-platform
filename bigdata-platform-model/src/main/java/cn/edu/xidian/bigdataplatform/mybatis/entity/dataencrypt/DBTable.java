package cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt;

import java.util.List;

public class DBTable {
    private String name;
    List<DBColumn> columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
    }
}
