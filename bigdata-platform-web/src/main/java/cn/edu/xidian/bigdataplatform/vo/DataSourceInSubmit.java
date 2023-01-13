package cn.edu.xidian.bigdataplatform.vo;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-08
 * @description:
 */
public class DataSourceInSubmit {
    private MySQLSource mySQLSource;
    private SpiderSource spiderSource;
    // 1 mysql 2爬虫
    private String type;

    public DataSourceInSubmit() {
    }

    public MySQLSource getMySQLSource() {
        return mySQLSource;
    }

    public void setMySQLSource(MySQLSource mySQLSource) {
        this.mySQLSource = mySQLSource;
    }

    public SpiderSource getSpiderSource() {
        return spiderSource;
    }

    public void setSpiderSource(SpiderSource spiderSource) {
        this.spiderSource = spiderSource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
