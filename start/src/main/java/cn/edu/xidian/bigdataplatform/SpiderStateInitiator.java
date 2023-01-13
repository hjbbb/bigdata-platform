package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.bean.SpiderTaskDTO;
import cn.edu.xidian.bigdataplatform.mybatis.entity.SpiderTask;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.SpiderTaskMapper;
import cn.edu.xidian.bigdataplatform.spider.SpiderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-22
 * @description:
 */
@Component
public class SpiderStateInitiator implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(SpiderStateInitiator.class);

    @Autowired
    private SpiderTaskMapper spiderTaskMapper;

    @Autowired
    private SpiderManager spiderManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        probeAndUpdateSpiderTaskInBatch();
    }

    @Async
    public void probeAndUpdateSpiderTaskInBatch() {
        List<SpiderTask> spiderTasks = spiderTaskMapper.findAllSpiderTask();
        HashMap<SpiderTask, Boolean> spiderStatus = new HashMap<>();
        for (SpiderTask spiderTask : spiderTasks) {
            String spiderName = spiderTask.getName();
            boolean isRunning = spiderManager.probeSpiderByName(spiderName);
            int oldStatus = spiderTask.getStatus();
            if (isRunning) {
                spiderTask.setStatus(1);
            } else {
                spiderTask.setStatus(2);
            }
            boolean isUpdateSuccess = spiderTaskMapper.updateSpiderTaskStatus(spiderTask);
            if (isUpdateSuccess) {
                logger.debug("update [" + spiderName + "] from " + oldStatus + " " + spiderTask.getStatus());
            } else {
                logger.warn("update [" + spiderName + "] status failed");
            }
            spiderStatus.put(spiderTask, isRunning);
        }
    }


}
