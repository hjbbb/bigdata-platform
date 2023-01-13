package cn.edu.xidian.bigdataplatform.bean;

import org.springframework.web.bind.annotation.RequestParam;

public class MetaDataAlterRequest {
    private String dataSourceId;
    private String tableName;
    private String columnName;
    private MetaDataItem metaDataItem;

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public MetaDataItem getMetaDataItem() {
        return metaDataItem;
    }

    public void setMetaDataItem(MetaDataItem metaDataItem) {
        this.metaDataItem = metaDataItem;
    }
}
