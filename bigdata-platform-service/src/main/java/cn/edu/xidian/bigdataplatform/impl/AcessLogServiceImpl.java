package cn.edu.xidian.bigdataplatform.impl;


import cn.edu.xidian.bigdataplatform.AccessLogService;
import cn.edu.xidian.bigdataplatform.bean.AccessLogPageResult;
import cn.edu.xidian.bigdataplatform.bean.MaskingTaskPageResult;
import cn.edu.xidian.bigdataplatform.mybatis.entity.AccessLog;
import cn.edu.xidian.bigdataplatform.mybatis.entity.Maskpojo;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.AccessLogMapper;
import cn.edu.xidian.bigdataplatform.utils.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AcessLogServiceImpl implements AccessLogService {

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Override
    public List<AccessLog> searchAllAccessLog() {
        List<AccessLog> accessLogs = accessLogMapper.searchAllAccessLog();
        return accessLogs;
    }

    @Override
    public AccessLogPageResult getPagedAccessLog(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AccessLog> accesslogList = accessLogMapper.searchAllAccessLog();
        PageInfo<AccessLog> info = new PageInfo<>(accesslogList);
        AccessLogPageResult queriedPage = new AccessLogPageResult();
        queriedPage.sourcesInPage = accesslogList;
        queriedPage.currentPage = info.getPages();
        queriedPage.totalPage = Long.valueOf(info.getTotal()).intValue();
        return queriedPage;
    }

    @Override
    public AccessLogPageResult getPagedAccessLogByName(int pageNum, int pageSize,String username) {
        AccessLog accessLog = new AccessLog();
        accessLog.setUsername(username);
        PageHelper.startPage(pageNum, pageSize);
        List<AccessLog> accesslogList = accessLogMapper.searchAllAccessLogByName(accessLog);
        PageInfo<AccessLog> info = new PageInfo<>(accesslogList);
        AccessLogPageResult queriedPage = new AccessLogPageResult();
        queriedPage.sourcesInPage = accesslogList;
        queriedPage.currentPage = info.getPages();
        queriedPage.totalPage = Long.valueOf(info.getTotal()).intValue();
        return queriedPage;
    }


    @Override
    @Nullable
    public AccessLog addNewAccessLog(AccessLog accessLog) {
        String uuid = UUIDUtil.genUUID();
        accessLog.setId(uuid);
        int line = accessLogMapper.insertAccessLog(accessLog);
        if (line != 1) {
            return null;
        }
        return accessLog;
    }

    @Override
    public AccessLog addAccesslog(String username,String operate) {
        AccessLog accessLog = new AccessLog();
        String uuid = UUIDUtil.genUUID();
        accessLog.setId(uuid);
        accessLog.setUsername(username);
        accessLog.setOperate(operate);
        accessLog.setOperatetime(LocalDateTime.now());
        accessLog.setId(uuid);
        int line = accessLogMapper.insertAccessLog(accessLog);
        if (line != 1) {
            return null;
        }
        return accessLog;
    }



}

