package cn.edu.xidian.bigdataplatform.bean;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;

import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-06
 * @description:
 */
public class DataSourcePageResult {
    public List<DataSource> sourcesInPage;
    public int currentPage;
    public int totalPage;
}
