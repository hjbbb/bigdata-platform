package cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt;

import java.util.List;
import java.util.Vector;

public class TableData {
    private List<String> columns;
    private List<List<String>> data;

    private long totalCount;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
