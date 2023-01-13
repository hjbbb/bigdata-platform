package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.CleansingService;
import cn.edu.xidian.bigdataplatform.DataSourceService;
import cn.edu.xidian.bigdataplatform.IngestionService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.base.ResultCode;
import cn.edu.xidian.bigdataplatform.bean.*;
import cn.edu.xidian.bigdataplatform.canal.*;
import cn.edu.xidian.bigdataplatform.hadoop.HDFSAccessor;
import cn.edu.xidian.bigdataplatform.hadoop.HadoopConnector;
import cn.edu.xidian.bigdataplatform.hadoop.bean.FileStatusBean;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTask;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskCanalInstance;
import cn.edu.xidian.bigdataplatform.mybatis.entity.CleansingTaskSparkApp;
import cn.edu.xidian.bigdataplatform.mybatis.entity.ConfigWriter;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.MySQLSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.SpiderSource;
import cn.edu.xidian.bigdataplatform.seatunnel.SeatunnelStarter;
import cn.edu.xidian.bigdataplatform.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.ibatis.jdbc.SQL;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.tools.utility.SchemaCrawlerUtility;
import us.fatehi.utility.datasource.DatabaseConnectionSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.edu.xidian.bigdataplatform.seatunnel.SeatunnelStarter.appId;

@RestController
public class GreetingController {

	public static final String basePath = "/usr/local/seatunnel-xd/config/";
	private static Logger logger = LoggerFactory.getLogger(GreetingController.class);

	@Autowired
	private DataSourceService dataSourceService;

	@Autowired
	private CleansingService cleansingService;

	@Autowired
	private IngestionService ingestionService;

	@Autowired
	private HadoopConnector hadoopConnector;

	@Autowired
	private HDFSAccessor accessor;

	@GetMapping("/api/testJSON")
	public Result hello() {
		return Result.success("请求成功，返回数据");
	}

	@GetMapping("/api/testVue")
	public Result vue() {
		return Result.success("请求成功，返回数据 -- vue");
	}

//	@GetMapping("/api/getSources")
//	public Result getSources(@RequestParam(name="pageNum") String pageNum, @RequestParam(name="pageSize") String pageSize) {
//		return Result.success(dataSourceService.getPagedDataSources(Integer.parseInt(pageNum), Integer.parseInt(pageSize)));
//	}

	// 数据源管理获得数据源，进入数据清洗页面时会调用
	@GetMapping("/api/getAllSources")
	public Result getAllSources() {
		List<MySQLSource> mySQLSources = dataSourceService.getMySQLSources();
		List<SpiderSource> spiderSources = dataSourceService.getSpiderSources();
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
		
		DataSourceList dataSourceList = new DataSourceList();
		dataSourceList.setMySQLSources(mySQLSources);
		dataSourceList.setSpiderSources(spiderSources);
		dataSourceList.setpSpaceSources(pSpaceSources);
		return Result.success(dataSourceList);
	}


	@PostMapping(value = "/api/addSource")
	public Result addSource(@RequestBody DataSourceInSubmit source) {
		if (source.getType().equals("MySQL")) {
			MySQLSource insertResult = dataSourceService.addNewMySQLDataSource(source.getMySQLSource());
			if (insertResult == null) {
				return Result.failed(ResultCode.DATASOURCE_ADD_FAILED);
			}
		} else if (source.getType().equals("Spider")) {
			SpiderSource insertResult = dataSourceService.addNewSpiderSource(source.getSpiderSource());
			if (insertResult == null) {
				return Result.failed(ResultCode.FAILED);
			}
		}
		return Result.success(true);
	}

	@PostMapping(value = "/api/modifySource")
	public Result modifySource(@RequestBody DataSourceInSubmit source) {
		if (source.getType().equals("MySQL")) {
			MySQLSource insertResult = dataSourceService.modifyMySQLDataSource(source.getMySQLSource());
			if (insertResult == null) {
				return Result.failed(ResultCode.DATASOURCE_ADD_FAILED);
			}
		} else if (source.getType().equals("Spider")) {
			SpiderSource insertResult = dataSourceService.modifySpiderDataSource(source.getSpiderSource());
			if (insertResult == null) {
				return Result.failed(ResultCode.FAILED);
			}
		}
		return Result.success(true);
	}

	@GetMapping(value = "/api/deleteSource")
	public Result deleteSource(@RequestParam(name="sourceId") String sourceId, @RequestParam(name="sourceType") String type) {
		boolean insertResult = true;
		insertResult = dataSourceService.deleteDataSource(sourceId, type);
		return Result.success(insertResult);
	}
	//start 公司数据表导入
		// Mysql数据导入         /加了时区记得删除/          path路径为dataX/job目录
	@PostMapping(value = "/api/submitDataX")
	@ResponseBody
	public Result submitDataX(@RequestBody DataXVO dataX) {
		DataXDTO dataXDTO = new DataXDTO();
		dataXDTO.setId(dataX.getId());
		dataXDTO.setDbName(dataX.getDbName());
		dataXDTO.setsIp(dataX.getsIp());
		dataXDTO.setsPort(dataX.getsPort());
		dataXDTO.setSourceUsername(dataX.getSourceUsername());
		dataXDTO.setSourcePassword(dataX.getSourcePassword());
		dataXDTO.setpIp(dataX.getpIp());
		dataXDTO.setpPort(dataX.getpPort());
		dataXDTO.setPlatUserName(dataX.getPlatUserName());
		dataXDTO.setPlatPassword(dataX.getPlatPassword());
		DataXDTO importDataX = ingestionService.submitDataX(dataXDTO);
		return Result.success();
	}

	//pSpace数据导入
	@PostMapping(value = "/api/submitPSpace")
	@ResponseBody
	public Result submitPSpace(@RequestBody PSpaceVO pSpace){
		PSpaceDTO pSpaceDTO = new PSpaceDTO();
		pSpaceDTO.setUrl(pSpace.getUrl());
		pSpaceDTO.setUser(pSpace.getUser());
		pSpaceDTO.setPassword(pSpace.getPassword());
		pSpaceDTO.setStart(pSpace.getStart());
		pSpaceDTO.setEnd(pSpace.getEnd());
		pSpaceDTO.setSqlIP(pSpace.getSqlIP());
		pSpaceDTO.setSqlPort(pSpace.getSqlPort());
		pSpaceDTO.setSqlUser(pSpace.getSqlUser());
		pSpaceDTO.setPassword(pSpace.getPassword());
		pSpaceDTO.setDataType(pSpace.getDataType());
		PSpaceDTO submitPSpace = ingestionService.submitPSpace(pSpaceDTO);
		return Result.success();
	}
	//end 公司数据导入

	@GetMapping(value = "/api/updatePSpace")
	public Result updatePSpace(@RequestParam String url, @RequestParam String user, @RequestParam String password){
		boolean updateResult = dataSourceService.updatePSpaceSource(url, user, password);
		// update之后自动启动爬取
		List<PSpaceDTO> pSpaceSources = dataSourceService.getPSpaceSources();
		for (PSpaceDTO pSpaceSource : pSpaceSources) {
			ingestionService.submitPSpace(pSpaceSource);
		}
		if (updateResult) {
			return Result.success();
		}
		return Result.failed(ResultCode.FAILED);
	}



	//start 爬虫增删改查
		//启动爬虫
	@GetMapping(value = "/api/launchSpider")
	public Result launchSpider(@RequestParam(name="id") String id) {
		try {
			SpiderSourceDTO spiderFound = ingestionService.findSpiderById(Integer.parseInt(id));
			SpiderSourceDTO launchedSpiderTask= ingestionService.launchSpider(spiderFound);
			return Result.success(launchedSpiderTask);
		}catch (Throwable e){
			return Result.failed(ResultCode.FAILED);
		}
	}
		//停止爬虫
	@GetMapping(value = "/api/stopSpider")
	public Result stopSpider(@RequestParam(name="id") String id) {
		try {
			SpiderSourceDTO spiderFound = ingestionService.findSpiderById(Integer.parseInt(id));
			SpiderSourceDTO stoppedSpiderTask = ingestionService.stopSpider(spiderFound);
			return Result.success(stoppedSpiderTask);
		}catch (Throwable e){
			return Result.failed(ResultCode.FAILED);
		}
	}
    	//暂停爬虫
	@GetMapping(value = "/api/pauseSpider")
	public Result pauseSpider(@RequestParam(name="id") String id){
		try {
			SpiderSourceDTO spiderFound = ingestionService.findSpiderById(Integer.parseInt(id));
			SpiderSourceDTO pausedSpiderTask = ingestionService.pauseSpider(spiderFound);
			return Result.success(pausedSpiderTask);
		}catch (Throwable e){
			return Result.failed(ResultCode.FAILED);
		}

	}
		//获取所有爬虫任务
	@GetMapping(value = "/api/getSpiderTasks")
	public Result getSpiderTasks() {
		List<SpiderSourceDTO> sources = ingestionService.fetchAllSpiderSources();
		List<SpiderSourceVO> views = new ArrayList<>();
		for (SpiderSourceDTO source : sources) {
			SpiderSourceVO view = new SpiderSourceVO();
			view.setId(source.getId());
			view.setName(source.getName());
			view.setSink(source.getSink());
			view.setWebsite(source.getWebsite());
			view.setDescription(source.getDescription());
			view.setCreateTime(source.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			view.setStatus(source.getStatus());
			view.setMinute(source.getMinute());
			view.setHour(source.getHour());
			view.setFrequency(source.getFrequency());
			view.setValue(source.getValue());
			views.add(view);
		}
		return Result.success(views);
	}

		//更新爬虫任务
	@PostMapping(value = "/api/updateSpiderTask")
	@ResponseBody
	public Result updateSpiderTask(@RequestBody SpiderSourceVO spiderTask) {
		try {
			SpiderSourceDTO spiderSource = new SpiderSourceDTO();
			String id = ingestionService.findSpiderByName(spiderTask.getName());
			if(id==null){
				return Result.failed(ResultCode.SPIDER_TASK_NOT_EXIST);
			}else if(ingestionService.findSpiderById(Integer.parseInt(id)).getStatus().equals("1")){
				return Result.failed(ResultCode.SPIDER_TASK_RUNNING);
			}else{
				spiderSource.setId(id);
				spiderSource.setName(spiderTask.getName());
				spiderSource.setDescription(spiderTask.getDescription());
				spiderSource.setWebsite(spiderTask.getWebsite());
				spiderSource.setCreateTime(LocalDateTime.now());
				spiderSource.setStatus("2");
				spiderSource.setSink(spiderTask.getSink());
				spiderSource.setMinute(spiderTask.getMinute());
				spiderSource.setHour(spiderTask.getHour());
				spiderSource.setFrequency(spiderTask.getFrequency());
				spiderSource.setValue(spiderTask.getValue());
				int data = ingestionService.updateSpider(spiderSource);
				return Result.success(data);
			}
		} catch (Throwable e) {
			return Result.failed(ResultCode.FAILED);
		}
	}
		//删除爬虫任务
    @GetMapping(value = "/api/deleteSpiderTask")
	public Result deleteSpiderTask(@RequestParam(name="id") String id) {
		try {
			int data = ingestionService.deleteSpider(id);
			if( data == 0){
				return Result.failed(ResultCode.SPIDER_TASK_NOT_EXIST);
			}else {
				return Result.success("删除成功");
			}
		} catch (Throwable e) {
			return Result.failed(ResultCode.FAILED);
		}
	}
		//根据爬虫名称获取爬虫任务id
	@GetMapping(value = "/api/findSpiderTaskIdByname")
	public Result findSpiderTaskIdByname(@RequestParam(name="name") String name) {
		try {
			String data = ingestionService.findSpiderByName(name);
			if(data==null){
				return Result.failed(ResultCode.SPIDER_TASK_NOT_EXIST);
			}else {
				return Result.success(data);
			}
		} catch (Throwable e) {
			return Result.failed(ResultCode.FAILED);
		}
	}
		//提交爬虫任务
	@PostMapping(value = "/api/submitSpider")
	@ResponseBody
	public Result submitSpider(@RequestBody SpiderSourceVO newSpider){
		try {
			String data = ingestionService.findSpiderByName(newSpider.getName());
			if(data==null){
				SpiderSourceDTO spiderSource = new SpiderSourceDTO();
				if(newSpider.getName().equals("*")){
					newSpider.setName("allSpider");
				}
				spiderSource.setName(newSpider.getName());
				spiderSource.setDescription(newSpider.getDescription());
				spiderSource.setWebsite(newSpider.getWebsite());
				spiderSource.setCreateTime(LocalDateTime.now());
				spiderSource.setStatus("2");
				spiderSource.setMinute(newSpider.getMinute());
				spiderSource.setHour(newSpider.getHour());
				spiderSource.setFrequency(newSpider.getFrequency());
				spiderSource.setValue(newSpider.getValue());
				logger.debug(spiderSource.toString());
				SpiderSourceDTO insertedSource = ingestionService.submitNewSpiderSource(spiderSource);
				return Result.success(insertedSource);
			}else {
				return Result.failed(ResultCode.SPIDER_TASK_REPEATE);
			}
		} catch (Throwable e) {
			return Result.failed(ResultCode.FAILED);
		}
	}
	//end 爬虫增删改查



	@PostMapping(value = "/api/submitMySQL")
	public Result submitMySQL(@RequestBody MySQLTaskVO mySQLVO) {
		MySQLTaskDTO mySQLTask = new MySQLTaskDTO();
		mySQLTask.setName(mySQLVO.getName());
		mySQLTask.setDescription(mySQLVO.getDescription());
		mySQLTask.setCreateTime(LocalDateTime.now());
		mySQLTask.setHost(mySQLVO.getHost());
		mySQLTask.setPort(mySQLVO.getPort());
		mySQLTask.setMode(mySQLVO.getMode());
		mySQLTask.setSchemaName(mySQLVO.getSchemaName());
		mySQLTask.setTargetTable(mySQLVO.getTargetTable());
		mySQLTask.setUsername(mySQLVO.getUsername());
		mySQLTask.setPassword(mySQLVO.getPassword());
		mySQLTask.setMode(mySQLVO.getMode());
		logger.debug(mySQLTask.toString());
		MySQLTaskDTO insertedTask = ingestionService.submitNewMySQLTask(mySQLTask);
		return Result.success(insertedTask);
	}

	@GetMapping(value = "/api/launchMySQL")
	public Result launchMySQL(@RequestParam(name="id") String id) {
		MySQLTaskDTO mySQLTaskFound = ingestionService.findMySQLById(Integer.parseInt(id));
		MySQLTaskDTO launchedMySQLTask = ingestionService.launchMySQLTask(mySQLTaskFound);
		return Result.success(launchedMySQLTask);
	}

	@GetMapping(value = "/api/stopMySQL")
	public Result stopMySQL(@RequestParam(name="id") String id) {
		MySQLTaskDTO mySQLTaskFound = ingestionService.findMySQLById(Integer.parseInt(id));
		MySQLTaskDTO stoppedMySQLTask = ingestionService.stopMySQLTask(mySQLTaskFound);
		return Result.success(stoppedMySQLTask);
	}

	@GetMapping(value = "/api/getMySQLTasks")
	public Result getMySQLTasks() {
		List<MySQLTaskDTO> tasks = ingestionService.fetchAllMySQLTasks();
		List<MySQLTaskVO> views = new ArrayList<>();
		for (MySQLTaskDTO task : tasks) {
			MySQLTaskVO view = new MySQLTaskVO();
			view.setId(task.getId());
			view.setName(task.getName());
			view.setDescription(task.getDescription());
			view.setMode(task.getMode());
			view.setHost(task.getHost());
			view.setPort(task.getPort());
			view.setCreateTime(task.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			view.setSchemaName(task.getSchemaName());
			view.setTargetTable(task.getTargetTable());
			view.setUsername(task.getUsername());
			view.setPassword(task.getPassword());
			view.setStatus(task.getStatus());
			views.add(view);
		}
		return Result.success(views);
	}


	@PostMapping("/api/checkMySQLSourceConnectivity")
	public Result checkMySQLSourceConnectivity(@RequestBody MySQLSource mySQLSource){
		boolean b = dataSourceService.checkMySQLConnectionSuccess(mySQLSource);
		return Result.success(b);
	}


	@PostMapping("/api/checkMySQLConnectivity")
	public Result checkMySQLConnectivity(@RequestBody MySQLTaskVO testMySQLVO){
		MySQLTaskDTO mySQLTask = new MySQLTaskDTO();
		mySQLTask.setName(testMySQLVO.getName());
		mySQLTask.setDescription(testMySQLVO.getDescription());
		mySQLTask.setCreateTime(LocalDateTime.now());
		mySQLTask.setHost(testMySQLVO.getHost());
		mySQLTask.setPort(testMySQLVO.getPort());
		mySQLTask.setMode(testMySQLVO.getMode());
		mySQLTask.setSchemaName(testMySQLVO.getSchemaName());
		mySQLTask.setTargetTable(testMySQLVO.getTargetTable());
		mySQLTask.setUsername(testMySQLVO.getUsername());
		mySQLTask.setPassword(testMySQLVO.getPassword());
		boolean b = ingestionService.checkMySQLConnectionSuccess(mySQLTask);
		return Result.success(b);
	}

	@GetMapping("/api/hdfsStats")
	public Result getHDFSStats() {
		// TODO
		return Result.success(new ArrayList<>());
	}

	@GetMapping("/api/listProcesses")
	public Result listProcesses() {
//		mainuser 22155 22154 28 21:40 pts/8    00:00:04 /usr/bin/python3 /usr/local/bin/scrapy crawl logistics
		HashMap<String, Boolean> result = new HashMap<>();
		List<SpiderSourceDTO> spiderTasks = ingestionService.fetchAllSpiderSources();
		for (SpiderSourceDTO spiderTask : spiderTasks) {
			String cmd = "ps -ef | grep scrapy | grep " + spiderTask.getName() + " | grep -v \"grep\"";
			ProcessBuilder builder = new ProcessBuilder(ImmutableList.of("bash", "-c", cmd));
			builder.redirectOutput();
			builder.redirectErrorStream(true);
			Process process = null;
			try {
				process = builder.start();
				process.waitFor();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
			InputStream inputStream = process.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			try {
				while ((line = bufferedReader.readLine()) != null) {
					logger.debug(line);
					if (line.contains("scrapy") && line.contains(spiderTask.getName())) {
						result.put(spiderTask.getName(), true);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Result.success(result);
	}

	@GetMapping("/api/hdfs/**")
	public Result getHDFSFiles(HttpServletRequest request) {
		logger.debug("getHDFSFiles");
		try {
			String requestURL = request.getRequestURL().toString();
			String[] list = requestURL.split("/api/hdfs");
			String path = list.length > 1 ? list[1] : "/";
			hadoopConnector.init();
			ArrayList<FileStatusBean> res = accessor.list(path);
			return Result.success(res);
		} catch (IOException e) {
			return Result.failed(ResultCode.FAILED);
		}
	}

	@GetMapping("/api/hdfs-download")
	public void getHDFSDownload(HttpServletResponse response, @RequestParam(value="path") String path) {
		logger.debug("getHDFSDownload");
		try {
			hadoopConnector.init();
			accessor.download(response, path);
		} catch (IOException e) {
		}
	}


	@PutMapping("/api/hdfs-mkdirs")
	public Result putHDFSMkdirs(@RequestBody Map<String, String> body) {
		logger.debug("putHDFSMkdirs");
		try {
			hadoopConnector.init();
			boolean res = accessor.mkdirs(body.get("path"));
			return Result.success(res);
		} catch (IOException e) {
			return Result.failed(ResultCode.FAILED);
		}
	}

	@PutMapping("/api/hdfs-rename")
	public Result putHDFSRename(@RequestParam(name="src") String src, @RequestParam(name="dest") String dest) {
		logger.debug("putHDFSRename");
		try {
			hadoopConnector.init();
			boolean res = accessor.rename(src, dest);
			return Result.success(res);
		} catch (IOException e) {
			return Result.failed(ResultCode.FAILED);
		}
	}

	@PostMapping("/api/hdfs/**")
	public Result postHDFSUpload(@RequestPart("files") List<MultipartFile> files, HttpServletRequest request) {
		logger.debug("postHDFSUpload");
		try {
			String requestURL = request.getRequestURL().toString();
			String dir = requestURL.split("/api/hdfs")[1];
			hadoopConnector.init();
			for (MultipartFile file : files) {
				accessor.upload(dir, file.getOriginalFilename(), file.getBytes());
			}
			return Result.success();
		} catch (IOException e) {
			return Result.failed(ResultCode.FAILED);
		}
	}

	@DeleteMapping("/api/hdfs/**")
	public Result<Boolean> deleteHDFSPath(HttpServletRequest request) {
		logger.debug("deleteHDFSPath");
		try {
			String requestURL = request.getRequestURL().toString();
			String path = requestURL.split("/api/hdfs")[1];
			hadoopConnector.init();
			boolean res = accessor.deletePath(path);
			return Result.success(res);
		} catch (IOException e) {
			return Result.failed(ResultCode.FAILED);
		}
	}

	//Start 贾皓api管理
	@GetMapping("api/showApi")
	public Result showApi(UserApiControl userApiControl){
		String res=userApiControl.showApi(thisSqlProperties);
		int status=userApiControl.status;
		if(status==1||status==2){
			return Result.failed(ResultCode.SQL_ERROR);
		}

		return Result.success(res);
	}
	@PostMapping("api/regsterApi")
	public Result regsterApi(@RequestBody UserApiControl userApiControl){
		int status=userApiControl.registerController(thisSqlProperties);
		if(status==1||status==2){
			return Result.failed(ResultCode.SQL_ERROR);
		}
		else  if(status==3){
			return Result.failed(ResultCode.API_REPEAT);
		}
		else if(status==4){
			return Result.failed(ResultCode.PARAM_NOT_COMPLETE);
		}

		return Result.success();
	}
	@PostMapping("api/deleteApi")
	public Result deleteApi(@RequestBody UserApiControl userApiControl){
		int status=userApiControl.delectApi(thisSqlProperties);
		if(status==1||status==2){
			return Result.failed(ResultCode.SQL_ERROR);
		}
		else if(status==4){
			return Result.failed(ResultCode.PARAM_NOT_COMPLETE);
		}
		return Result.success();
	}

	//Start 贾皓
	@Autowired
	propertiesRead thisSqlProperties;
	@PostMapping("/api/addMataData")
	public Result addMataData(@RequestBody MetaDataControl mataDataControl){
		int	status=mataDataControl.add(thisSqlProperties);
		if(status==1||status==2){
			return Result.failed(ResultCode.DATABASE_CONNOT_CONNECT);
		}else if (status==3){
			return Result.failed(ResultCode.FIELD_NOT_EXIST);
		}else if (status==4){
			return Result.failed(ResultCode.PARAM_NOT_COMPLETE);
		}
		return Result.success();
	}

	@PostMapping("/api/deleteMataData")
	public Result deleteMataData(@RequestBody MetaDataControl mataDataControl){
		int	status=mataDataControl.delect(thisSqlProperties);
		if(status==1||status==2){
			return Result.failed(ResultCode.DATABASE_CONNOT_CONNECT);
		}else if (status==3){
			return Result.failed(ResultCode.FIELD_NOT_EXIST);
		}else if (status==4){
			return Result.failed(ResultCode.PARAM_NOT_COMPLETE);
		}

		return Result.success();
	}

	@PostMapping("/api/updateMataData")
	public Result updateMataData(@RequestBody MetaDataControl mataDataControl){
		int	status=mataDataControl.updata(thisSqlProperties);
		if(status==1||status==2){
			return Result.failed(ResultCode.DATABASE_CONNOT_CONNECT);
		}else if (status==3){
			return Result.failed(ResultCode.SQL_ERROR);
		} else if (status==4){
			return Result.failed(ResultCode.PARAM_NOT_COMPLETE);
		}
		return Result.success();
	}
	@PostMapping("/api/showMataData")
	public Result showMataData(@RequestBody MetaDataControl mataDataControl){
		String res=mataDataControl.show(thisSqlProperties);
		int status=mataDataControl.getStatus();
		if(status==1||status==2||res==null){
			return Result.failed(ResultCode.DATABASE_CONNOT_CONNECT);
		}else if (status==3){
			return Result.failed(ResultCode.SQL_ERROR);
		} else if (status==4){
			return Result.failed(ResultCode.PARAM_NOT_COMPLETE);
		}
		return Result.success(res);
	}

	@PostMapping("/api/submitSparkConf")
	public Result submitSparkConf(@RequestBody InsertSparkConfig insertSparkConfig){
		int status=insertSparkConfig.exec(thisSqlProperties);
		if(status>0){
			return Result.failed(ResultCode.DATABASE_CONNOT_CONNECT);
		}
		return Result.success();
	}
	@PostMapping("/api/subSqlImport")
	public Result subSqlImport(@RequestBody importDatabaseBean importDatabaseBean){
		int status = importDatabaseBean.exec(thisSqlProperties);
		if(status>0){
			return Result.failed(ResultCode.DATABASE_CONNOT_CONNECT);
		}
		return Result.success();
	}

	@GetMapping("/api/ShowDatabases")
	public Result ShowDatabases(ShowData showEncData){
		String databases=showEncData.showDatabases(thisSqlProperties);
		if(showEncData.status>0){
			return Result.failed(ResultCode.FAILED);
		}
		return Result.success(databases);
	}
	@PostMapping("/api/Showtables")
	public Result Showtables(@RequestBody ShowData showData){
		String tables=showData.showTables(thisSqlProperties);
		if(showData.status>0){
			return Result.failed(ResultCode.FAILED);
		}
		return Result.success(tables);
	}
	@PostMapping("/api/ShowColumns")
	public Result ShowColumns(@RequestBody ShowData showData){
		String columns=showData.showColumns(thisSqlProperties);
		if(showData.status>0){
			return Result.failed(ResultCode.FAILED);
		}
		return Result.success(columns);
	}
	@PostMapping("/api/ShowSqlData")
	public Result ShowSqlData(@RequestBody ShowData showData){
		String columns=showData.showSqlData(thisSqlProperties);
		if(showData.status>0){
			return Result.failed(ResultCode.FAILED);
		}
		return Result.success(columns);
	}


	//end 贾皓

	@GetMapping("/api/getDataSourceInfo")
	public Result getDataSourceInfo(String sourceId) {
		try {
			MySQLSource mySQLSource = dataSourceService.findMySQLSourceById(sourceId);
			return Result.success(mySQLSource.fetchDataSourceInfo());
		} catch (SQLException e) {
			return Result.failed(ResultCode.FAILED);
		}
	}
}

class EsIndexCreator {
	public static void createEsIndex() throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("172.17.0.2", 9200, "http")));

		CreateIndexRequest request = new CreateIndexRequest("test");
		Map<String, Object> message = new HashMap<>();
		message.put("type", "text");
		Map<String, Object> properties = new HashMap<>();
		properties.put("message", message);
		Map<String, Object> type = new HashMap<>();
		type.put("properties", properties);
		Map<String, Object> mapping = new HashMap<>();
		mapping.put("type", type);
		request.mapping(mapping);
		CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		System.out.println("create index");
		System.out.println(String.valueOf(createIndexResponse.isAcknowledged()));
		client.close();
	}

	public static boolean isIndexExists(String indexName) throws IOException, ConnectException {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("172.17.0.2", 9200, "http")));
		GetAliasesRequest request = new GetAliasesRequest();
		List<Map<String, Object>> resultList = new ArrayList<>();
		GetAliasesResponse alias = client.indices().getAlias(request, RequestOptions.DEFAULT);
		Map<String, Set<AliasMetaData>> map = alias.getAliases();
		map.forEach((k, v) -> {
			if (!k.startsWith(".")) {//忽略elasticesearch 默认的
				Map map1 = new HashMap();
				map1.put("indexName", k);
				resultList.add(map1);
			}
		});

		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(resultList));

		for (Map<String, Object> e : resultList) {
			if (e.get("indexName").equals(indexName)) {
				client.close();
				return true;
			}
		}
		client.close();
		return false;
	}

	public static void main(String[] args) throws IOException {
		boolean isExists = isIndexExists("logistics_freight_source");
		System.out.println(isExists);
//		createEsIndex();
	}
}
