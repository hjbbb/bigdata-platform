package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.bean.DataXDTO;
import cn.edu.xidian.bigdataplatform.bean.MySQLTaskDTO;
import cn.edu.xidian.bigdataplatform.bean.PSpaceDTO;
import cn.edu.xidian.bigdataplatform.bean.SpiderSourceDTO;

import javax.xml.crypto.Data;
import java.util.List;

public interface IngestionService {
    SpiderSourceDTO launchSpider(SpiderSourceDTO spiderSource);

    SpiderSourceDTO findSpiderById(Integer id);

    SpiderSourceDTO stopSpider(SpiderSourceDTO spiderSource);

    SpiderSourceDTO pauseSpider(SpiderSourceDTO spiderSource);

    SpiderSourceDTO submitNewSpiderSource(SpiderSourceDTO spiderSource);

    String findSpiderByName(String name);

    int deleteSpider(String id);

    int updateSpider(SpiderSourceDTO spiderSource);

    List<SpiderSourceDTO> fetchAllSpiderSources();

    MySQLTaskDTO findMySQLById(Integer id);

    MySQLTaskDTO launchMySQLTask(MySQLTaskDTO mySQLTask);

    MySQLTaskDTO stopMySQLTask(MySQLTaskDTO mysqlTask);

    MySQLTaskDTO submitNewMySQLTask(MySQLTaskDTO mySQLTaskDTO);

    List<MySQLTaskDTO> fetchAllMySQLTasks();

    boolean checkMySQLConnectionSuccess(MySQLTaskDTO mySQLTaskDTO);

    DataXDTO submitDataX(DataXDTO dataX);

    PSpaceDTO submitPSpace(PSpaceDTO pSpaceDTO);
}
