package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.CleansingService;
import cn.edu.xidian.bigdataplatform.IngestionService;
import cn.edu.xidian.bigdataplatform.SearchService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.bean.CleansingTaskBean;
import cn.edu.xidian.bigdataplatform.bean.MySQLTaskDTO;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.FreightSource;
import cn.edu.xidian.bigdataplatform.vo.SearchTableDataVO;
import cn.edu.xidian.bigdataplatform.vo.SearchableTableVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.xidian.bigdataplatform.base.ResultCode;
import cn.edu.xidian.bigdataplatform.hadoop.bean.FileStatusBean;
import cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchResponse.ResultSearch;
import cn.edu.xidian.bigdataplatform.SearchService;
import cn.edu.xidian.bigdataplatform.base.Result;

import cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchRequest.RequestSearch;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-24
 * @description: 检索Controller
 */
@RestController
public class SearchController {
    private static Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private IngestionService ingestionService;

    @Autowired
    private CleansingService cleansingService;

    @GetMapping(value = "/api/searchFreight")
    public Result searchFreight(@RequestParam(name="depart") String depart, @RequestParam(name = "destination") String destination,
                                @RequestParam(name="distance") String distance, @RequestParam(name = "freightDescription") String freightDescription,
                                @RequestParam(name="transportType") String transportType) {
        List<FreightSource> freightSources = searchService.searchFreightSourceByDepartAndDestination(depart, destination, distance, freightDescription, transportType);
        return Result.success(freightSources);
    }

    @GetMapping(value = "/api/getSearchableTables")
    public Result getSearchableTables() {
        List<MySQLTaskDTO> mySQLTask = ingestionService.fetchAllMySQLTasks();
        List<SearchableTableVO> tables = new ArrayList<>();
        for (MySQLTaskDTO task : mySQLTask) {
            SearchableTableVO table = new SearchableTableVO();
            table.setId(task.getId());
            table.setName(task.getName());
            table.setType("MySQL");
            tables.add(table);
        }
        return Result.success(tables);
    }

    @GetMapping(value = "/api/getTableDataToSearch")
    public Result getTableDataToSearch(@RequestParam(name = "tableId") String tableId, @RequestParam("tableType") String tableType) {

        List<String> columns = new ArrayList<>();
        List<FreightSource> allFreight = new ArrayList<>();
        if (tableType.equals("MySQL")) {
            MySQLTaskDTO ingestionTask = ingestionService.findMySQLById(Integer.valueOf(tableId));
            List<CleansingTaskBean.SingleColumn> columnList = cleansingService.fetchSchema(ingestionTask.getSchemaName(), ingestionTask.getTargetTable());
            for (CleansingTaskBean.SingleColumn column : columnList) {
                columns.add(column.columnName);
            }
            allFreight = searchService.findAllFreight();
        }
        SearchTableDataVO tableData = new SearchTableDataVO();
        tableData.setData(allFreight);
        tableData.setColumns(columns);
        return Result.success(tableData);
    }

    @PostMapping(value = "/api/search")
    @ResponseBody
    public Result startSearch(@RequestBody RequestSearch requestSearch) throws Exception {
        JSONObject result = new JSONObject();
        ResultSearch resultSearch = searchService.getCommonSearch(requestSearch);
        result.put("searchResult", resultSearch);
        return Result.success(result.toJSONString());
    }


    @GetMapping("/api/getIndices")
    public Result getIndices() {
        try {
            String[] result = searchService.getAllIndices();
            return Result.success(result);
        } catch (Throwable e) {
            return Result.failed(ResultCode.INDEX_NAME_FAILED);
        }
    }
}