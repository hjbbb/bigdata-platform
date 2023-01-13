package cn.edu.xidian.bigdataplatform.vo;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;

import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-12-08
 * @description:
 */
public class DataSourceList {
    private List<MySQLSource> mySQLSources;
    private List<SpiderSource> spiderSources;

    private List<PSpaceVO> pSpaceSources;

    public DataSourceList() {

    }

    public List<MySQLSource> getMySQLSources() {
        return mySQLSources;
    }

    public void setMySQLSources(List<MySQLSource> mySQLSources) {
        this.mySQLSources = mySQLSources;
    }

    public List<SpiderSource> getSpiderSources() {
        return spiderSources;
    }

    public void setSpiderSources(List<SpiderSource> spiderSources) {
        this.spiderSources = spiderSources;
    }

    public List<PSpaceVO> getpSpaceSources() {
        return pSpaceSources;
    }

    public void setpSpaceSources(List<PSpaceVO> pSpaceSources) {
        this.pSpaceSources = pSpaceSources;
    }
}
