package cn.edu.xidian.bigdataplatform.vo;

import cn.edu.xidian.bigdataplatform.mybatis.entity.search.FreightSource;

import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-21
 * @description:
 */
public class SearchTableDataVO {
    private List<String> columns;
    private List<FreightSource> data;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<FreightSource> getData() {
        return data;
    }

    public void setData(List<FreightSource> data) {
        this.data = data;
    }
}
