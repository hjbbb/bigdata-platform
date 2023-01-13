package cn.edu.xidian.bigdataplatform.bean;

import cn.edu.xidian.bigdataplatform.mybatis.entity.Maskpojo;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;

import java.util.List;

public class MaskingTaskPageResult {
    public List<Maskpojo> sourcesInPage;
    public int currentPage;
    public int totalPage;
}
