package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.impl.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("/api")
public class DownloadController {
    @Resource
    private HttpServletResponse response;

    @Resource
    private HttpServletRequest request;

    @GetMapping(value = "/down/file")
    public void downloadFileStream(@RequestParam(name="filepath") String filepath,@RequestParam(name="filename") String name,@RequestParam(name="savetype") String savetype) {
        // 文件本地位置
//        String filePath = "C:\\Users\\perkydodo\\Desktop\\记录日志.txt";
        String[] str2;
        str2 = new String[]{".txt",".xlsx",".sql"};
        int i = Integer.valueOf(savetype).intValue();
        String filetype = str2[i];
        String filePath = "D:\\下载\\d18n-main\\d18n-main\\masklog\\"+filepath+filetype;
        System.out.println(filePath);
        String filename = name+filetype;
        // 文件名称
//        String fileName = "0002.txt";
        File file = new File(filePath);
        FileUtil.downloadFile(file, request, response, filename);

        // 浏览器访问：http://x.x.x.x/test/down/file
    }
}

