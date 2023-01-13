package cn.edu.xidian.bigdataplatform.impl;

import cn.edu.xidian.bigdataplatform.DataSourceService;
import cn.edu.xidian.bigdataplatform.bean.DataSourcePageResult;
import cn.edu.xidian.bigdataplatform.bean.MySQLTaskDTO;
import cn.edu.xidian.bigdataplatform.bean.PSpaceDTO;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.PSpace;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.DataSourceMapper;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.MySQLSourceMapper;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.PSpaceMapper;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.SpiderSourceMapper;
import cn.edu.xidian.bigdataplatform.utils.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataSourceServiceImpl implements DataSourceService {
    private static Logger logger = LoggerFactory.getLogger(DataSourceServiceImpl.class);

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private MySQLSourceMapper mySQLSourceMapper;

    @Autowired
    private SpiderSourceMapper spiderSourceMapper;

    @Autowired
    private PSpaceMapper pSpaceMapper;


    @Override
    public DataSourcePageResult getPagedDataSources(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DataSource> sourceList = dataSourceMapper.queryAllDataSources();
        PageInfo<DataSource> info = new PageInfo<>(sourceList);
        DataSourcePageResult queriedPage = new DataSourcePageResult();
        queriedPage.sourcesInPage = sourceList;
        queriedPage.currentPage = info.getPages();
        queriedPage.totalPage = Long.valueOf(info.getTotal()).intValue();
        return queriedPage;
    }

    @Override
    public List<DataSource> getAllDataSources() {
        return dataSourceMapper.queryAllDataSources();
    }

    @Override
    public List<MySQLSource> getMySQLSources() {
        return mySQLSourceMapper.queryAllMySQLSources();
    }

    @Override
    public List<SpiderSource> getSpiderSources() {
        return spiderSourceMapper.queryAllSpiderSources();
    }

    @Override
    public SpiderSource addNewSpiderSource(SpiderSource spiderSource) {
        spiderSource.setCreateTime(LocalDateTime.now());
        int line = spiderSourceMapper.insertNewSpiderSource(spiderSource);
        if (line != 1) {
            return null;
        }
        return spiderSource;
    }

    @Override
    @Nullable
    public MySQLSource addNewMySQLDataSource(MySQLSource mySQLSource) {
        mySQLSource.setCreateTime(LocalDateTime.now());
        int line = mySQLSourceMapper.insertNewMySQLSource(mySQLSource);
        if (line != 1) {
            return null;
        }
        return mySQLSource;
    }

    @Override
    @Nullable
    public MySQLSource modifyMySQLDataSource(MySQLSource mySQLSource) {
        int line = mySQLSourceMapper.updateExistedMySQLSourceById(mySQLSource);
        if (line != 1) {
            return null;
        }
        return mySQLSource;
    }

    @Override
    @Nullable
    public SpiderSource modifySpiderDataSource(SpiderSource spiderSource) {
        int line = spiderSourceMapper.updateExistedSpiderSourceById(spiderSource);
        if (line != 1) {
            return null;
        }
        return spiderSource;
    }

    @Override
    public boolean deleteDataSource(String sourceId, String sourceType) {
        int line = 1;
        if (sourceType.equals("MySQL")) {
            line = mySQLSourceMapper.deleteExistedSourceById(Integer.parseInt(sourceId));
        } else if (sourceType.equals("Spider")) {
            line = spiderSourceMapper.deleteExistedSourceById(Integer.parseInt(sourceId));
        }
        return line == 1;
    }


    @Override
    @Nullable
    public MySQLSource findMySQLSourceById(String sourceId) {
        MySQLSource source = mySQLSourceMapper.findMySQLSourceById(Integer.parseInt(sourceId));
        return source;
    }

    @Override
    @Nullable
    public SpiderSource findSpiderSourceById(String sourceId) {
        SpiderSource source = spiderSourceMapper.findSpiderSourceById(Integer.parseInt(sourceId));
        return source;
    }

    @Override
    public boolean checkMySQLConnectionSuccess(MySQLSource mySQLSource) {
        String url = "jdbc:mysql://" + mySQLSource.getHost() + ":" + mySQLSource.getPort(); //pointing to no database.
        String username = mySQLSource.getUsername();
        String password = mySQLSource.getPassword();
        logger.debug(url);
        logger.debug(username);
        logger.debug(password);
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (connection == null) {
                logger.error("Cannot connect the server [" + mySQLSource.getHost() + "]");
                return false;
            }

            boolean valid = connection.isValid(10);
            if (!valid) {
                logger.error("Cannot connect the server [" + mySQLSource.getHost() + "]");
            }
            connection.close();
            return valid;
        } catch (SQLException e) {
            logger.error("Cannot connect the server [" + mySQLSource.getHost() + "]", e);
            return false;
        }
    }

    @Override
    public List<PSpaceDTO> getPSpaceSources() {
        List<PSpace> pSpaceModels = pSpaceMapper.queryAllPSpaceSources();
        List<PSpaceDTO> pSpaceDTOList = new ArrayList<>();
        for (PSpace model : pSpaceModels) {
            PSpaceDTO pSpaceDTO = new PSpaceDTO();
            pSpaceDTO.setUrl(model.getUrl());
            pSpaceDTO.setUser(model.getUser());
            pSpaceDTO.setPassword(model.getPassword());
            pSpaceDTO.setStart(model.getStart());
            pSpaceDTO.setEnd(model.getEnd());
            pSpaceDTO.setSqlIP(model.getSqlIP());
            pSpaceDTO.setSqlPort(model.getSqlPort());
            pSpaceDTO.setSqlUser(model.getSqlUser());
            pSpaceDTO.setPassword(model.getPassword());
            pSpaceDTO.setDataType(model.getDataType());
            pSpaceDTOList.add(pSpaceDTO);
        }
        return pSpaceDTOList;
    }

    @Override
    public PSpace findPSpaceByDataType(String dataType) {
        return pSpaceMapper.findPSpaceByDataType(dataType);
    }

    @Override
    public boolean updatePSpaceSource(String url, String user, String password) {
        int lines = pSpaceMapper.updatePSpace(url, user, password);
        return lines != -1;
    }
}
