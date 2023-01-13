package cn.edu.xidian.bigdataplatform.bean;

import cn.edu.xidian.bigdataplatform.mybatis.entity.AccessLog;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;

import java.util.List;

public class AccessLogPageResult {

    public List<AccessLog> sourcesInPage;
    public int currentPage;
    public int totalPage;
}
