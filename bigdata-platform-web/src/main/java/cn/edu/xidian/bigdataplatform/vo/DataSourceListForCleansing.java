package cn.edu.xidian.bigdataplatform.vo;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;

import java.util.HashMap;
import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-10
 * @description:
 */
public class DataSourceListForCleansing {

    public static class MySQLSourceInCleansing {
        // 数据源对应的数据库
        MySQLSource mySQLSource;
        // 数据库中的表名
        List<String> tables;

        public MySQLSourceInCleansing() {
        }

        public MySQLSource getMySQLSource() {
            return mySQLSource;
        }

        public void setMySQLSource(MySQLSource mySQLSource) {
            this.mySQLSource = mySQLSource;
        }

        public List<String> getTables() {
            return tables;
        }

        public void setTables(List<String> tables) {
            this.tables = tables;
        }
    }

    public static class SpiderSourceInCleansing {
        // 数据源对应的数据库
        SpiderSource spiderSource;
        // 数据库中的表名
        List<String> tables;

        public SpiderSourceInCleansing() {
        }

        public SpiderSource getSpiderSource() {
            return spiderSource;
        }

        public void setSpiderSource(SpiderSource spiderSource) {
            this.spiderSource = spiderSource;
        }

        public List<String> getTables() {
            return tables;
        }

        public void setTables(List<String> tables) {
            this.tables = tables;
        }
    }
    private List<MySQLSourceInCleansing> mysqlList;
    private List<SpiderSourceInCleansing> spiderList;
    private PSpaceVO pSpaceVO;

    public DataSourceListForCleansing() {
    }

    public List<MySQLSourceInCleansing> getMysqlList() {
        return mysqlList;
    }

    public void setMysqlList(List<MySQLSourceInCleansing> mysqlList) {
        this.mysqlList = mysqlList;
    }

    public List<SpiderSourceInCleansing> getSpiderList() {
        return spiderList;
    }

    public void setSpiderList(List<SpiderSourceInCleansing> spiderList) {
        this.spiderList = spiderList;
    }
}
