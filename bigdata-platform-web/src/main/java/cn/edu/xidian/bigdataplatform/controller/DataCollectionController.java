package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.DataSourceService;
import cn.edu.xidian.bigdataplatform.bean.PSpaceDTO;
import cn.edu.xidian.bigdataplatform.vo.DataCollectionTaskList;
import cn.edu.xidian.bigdataplatform.vo.PSpaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.xidian.bigdataplatform.base.Result;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DataCollectionController {
    @Autowired
    DataSourceService dataSourceService;
    @GetMapping("/api/data-collection")
    public Result getAllCollectionTask() {
        List<PSpaceDTO> pSpaceDTOList = dataSourceService.getPSpaceSources();
        List<PSpaceVO> pSpaceSources = new ArrayList<>();
        for (PSpaceDTO pSpace : pSpaceDTOList) {
            PSpaceVO pSpaceVO = new PSpaceVO();
            pSpaceVO.setUrl(pSpace.getUrl());
            pSpaceVO.setUser(pSpace.getUser());
            pSpaceVO.setPassword(pSpace.getPassword());
            pSpaceVO.setStart(pSpace.getStart());
            pSpaceVO.setEnd(pSpace.getEnd());
            pSpaceVO.setSqlIP(pSpace.getSqlIP());
            pSpaceVO.setSqlPort(pSpace.getSqlPort());
            pSpaceVO.setSqlUser(pSpace.getSqlUser());
            pSpaceVO.setPassword(pSpace.getPassword());
            pSpaceVO.setDataType(pSpace.getDataType());
            pSpaceSources.add(pSpaceVO);
        }
        DataCollectionTaskList dataCollectionTaskList = new DataCollectionTaskList();
        dataCollectionTaskList.setpspaceSources(pSpaceSources);
        return Result.success(dataCollectionTaskList);
    }
}
