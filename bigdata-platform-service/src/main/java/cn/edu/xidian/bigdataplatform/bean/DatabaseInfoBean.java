package cn.edu.xidian.bigdataplatform.bean;

import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-21
 * @description:
 */
public class DatabaseInfoBean {
    private List<Schema> schemas;
    private List<Table> tables;

    public List<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
