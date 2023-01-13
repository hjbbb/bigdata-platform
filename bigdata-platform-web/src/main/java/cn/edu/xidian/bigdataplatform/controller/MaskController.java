package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.AccessLogService;
import cn.edu.xidian.bigdataplatform.MaskService;
//import cn.edu.xidian.bigdataplatform.MaskingTaskService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.base.ResultCode;
import cn.edu.xidian.bigdataplatform.impl.FileUtil;
import cn.edu.xidian.bigdataplatform.mybatis.entity.Maskpojo;
import cn.edu.xidian.bigdataplatform.mybatis.entity.user.User;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;
import cn.edu.xidian.bigdataplatform.utils.UUIDUtil;
import cn.edu.xidian.bigdataplatform.vo.LoginVO;
import cn.edu.xidian.bigdataplatform.vo.LogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MaskController  {

    @Autowired
    private MaskService maskService;

    @Autowired
    private AccessLogService accessLogService;

    @Resource
    private HttpServletResponse response;

    @Resource
    private HttpServletRequest request;

    @GetMapping("/api/dodo")
    public List<Maskpojo> getAllMask(){
        List<Maskpojo> maskpojos = maskService.searchAllMask();
        return maskpojos;
    }
    @GetMapping("/api/getMask")
    public Result getMasking(@RequestParam(name="pageNum") String pageNum, @RequestParam(name="pageSize") String pageSize) {
        return Result.success(maskService.getPagedMaskTask(Integer.parseInt(pageNum), Integer.parseInt(pageSize)));
    }

    @PostMapping(value = "/api/submitMask")
    public Result addMask(@RequestBody Maskpojo maskpojo) throws IOException {
        Integer maskway = maskpojo.getMaskway();
        Integer savetype = maskpojo.getSavetype();
        maskpojo.setCreatetime(LocalDateTime.now());
        String[] str1;
        String[] str2;
        str1 = new String[]{"默认","随机洗牌","哈希CRC32","哈希MD5","遮盖"};
        str2 = new String[]{".txt",".xlsx",".sql"};
        maskpojo.setMaskwaytext(str1[maskway]);
        maskpojo.setSavetypetext(str2[savetype]);
        Maskpojo insertResult = maskService.addNewMask(maskpojo);
        String schema = insertResult.getSchema();
        String table = insertResult.getTable();
        String uuid = insertResult.getUuid();
        insertResult.setMaskwaytext(str1[maskway]);
        insertResult.setSavetypetext(str2[savetype]);
        maskpojo.setMaskwaytext(str1[maskway]);
        maskpojo.setSavetypetext(str2[savetype]);
//        System.out.println(schema);
//        System.out.println(insertResult.getMaskway());
//        System.out.println(insertResult.getSavetype());
        Boolean runMask = maskService.runMask(schema,table,uuid,maskway,savetype);
        System.out.println("#########################################");
        if (insertResult == null) {
            return Result.failed(ResultCode.DATASOURCE_ADD_FAILED);
        }
        return Result.success(insertResult);
    }

    @GetMapping("/api/getAccesslog")
    public Result getAccesslog(@RequestParam(name="pageNum") String pageNum, @RequestParam(name="pageSize") String pageSize) {
        return Result.success(accessLogService.getPagedAccessLog(Integer.parseInt(pageNum), Integer.parseInt(pageSize)));
    }
    @GetMapping("/api/getAccesslogByName")
    public Result getAccesslogByName(@RequestParam(name="pageNum") String pageNum, @RequestParam(name="pageSize") String pageSize,
                                     @RequestParam(name="username") String username) {
        return Result.success(accessLogService.getPagedAccessLogByName(Integer.parseInt(pageNum), Integer.parseInt(pageSize),username));
    }

    @PostMapping("/api/addlog")
    public Result logout(@RequestBody LogVO userInfo) {
        String username = userInfo.username;
        String operate = userInfo.operate;
        accessLogService.addAccesslog(username, operate);
        LoginVO vo = new LoginVO();
        vo.username = userInfo.username;
        return Result.success(vo);
    }

    @GetMapping(value = "/api/maskfile")
    public Result downloadFileStream() {
        // 文件本地位置
        String filePath = "C:\\Users\\perkydodo\\Desktop\\记录日志.txt";
        // 文件名称
        String fileName = "下载成功.txt";
        File file = new File(filePath);
        FileUtil.downloadFile(file, request, response, fileName);
        return Result.success("下载成功！");
        // 浏览器访问：http://x.x.x.x/test/down/file
    }
//    @RestController
//    @RequestMapping("/api")
//    public class TestController {
//        @Resource
//        private HttpServletResponse response;
//
//        @Resource
//        private HttpServletRequest request;
//
//        @GetMapping(value = "/maskfile")
//        public void downloadFileStream() {
//            // 文件本地位置
//            String filePath = "C:\\Users\\perkydodo\\Desktop\\记录日志.txt";
//            // 文件名称
//            String fileName = "下载成功.txt";
//            File file = new File(filePath);
//            FileUtil.downloadFile(file, request, response, fileName);
//            // 浏览器访问：http://x.x.x.x/test/down/file
//        }
//
//        @GetMapping  (value = "/maskfiles")
//        public void downloadFileStreamByName(@RequestParam(name="filename") String fileName) {
//            // 文件本地位置
//            // 文件名称
//            String filePath = "C:\\Users\\perkydodo\\Desktop\\记录日志.txt";
////            String fileName = "测试文件.txt";
//            File file = new File(filePath);
//            FileUtil.downloadFile(file, request, response, fileName);
//            // 浏览器访问：http://x.x.x.x/test/down/file
//        }
//    }



//    @GetMapping("/api/per")
//    public void per(){
//        System.out.println("###############访问/api/login");
//        String username = "dodo";
//        accessLogService.addAccesslog(username);
//    }
}
