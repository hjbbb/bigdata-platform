package cn.edu.xidian.bigdataplatform.impl;

import cn.edu.xidian.bigdataplatform.MaskService;
import cn.edu.xidian.bigdataplatform.bean.MaskingTaskPageResult;
import cn.edu.xidian.bigdataplatform.mybatis.entity.Maskpojo;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.MaskMapper;
import cn.edu.xidian.bigdataplatform.utils.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MaskServiceImpl implements MaskService {

    @Autowired
    private MaskMapper maskMapper;

    @Override
    public List<Maskpojo> searchAllMask() {
        List<Maskpojo> maskpojos = maskMapper.searchAllMask();
        return maskpojos;
    }

    @Override
    public MaskingTaskPageResult getPagedMaskTask(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Maskpojo> maskingList = maskMapper.searchAllMask();
        PageInfo<Maskpojo> info = new PageInfo<>(maskingList);
        MaskingTaskPageResult queriedPage = new MaskingTaskPageResult();
        queriedPage.sourcesInPage = maskingList;
        queriedPage.currentPage = info.getPages();
        queriedPage.totalPage = Long.valueOf(info.getTotal()).intValue();
        return queriedPage;
    }

    @Override
    @Nullable
    public Maskpojo addNewMask(Maskpojo maskpojo) {
        String uuid = UUIDUtil.genUUID();
        maskpojo.setUuid(uuid);
        int line = maskMapper.insertNewMask(maskpojo);
        if (line != 1) {
            return null;
        }
        return maskpojo;
    }

    @Override
    public Boolean runMask(String schema, String table,String uuid,Integer maskway,Integer savetype) throws IOException {
        String[] str1;
        String[] str2;
        str1 = new  String[]{"test/mask.csv","test/mask1.csv","test/mask2.csv","test/mask3.csv","test/mask4.csv"};
        str2 = new  String[]{".txt",".xlsx",".sql"};
        String maskway2 = str1[maskway];
        String savetype2 = str2[savetype];
        System.out.println(uuid);
        System.out.println(maskway2);
        System.out.println(savetype2);
        File fi = new File("/root/d18n");
        String cmd = "./bin/d18n";
        String cnf1 = "--defaults-extra-file";
        String cnf2 = "test/my.cnf";
        String mask1 = "--mask";
        String mask2 = maskway2;
        String DB1 = "--database";
        String DB2 = schema;
        String query1 = "--query";
        String query2 = "select * from "+table;
        String file1 = "--file";
        String file2 = "masklog/"+uuid+savetype2;
        String[] cmds = new String[] {cmd, cnf1, cnf2, mask1, mask2, DB1, DB2, query1, query2, file1, file2};
        System.out.println(cmds);
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(cmds)
//        processBuilder.command("./bin/d18n","--defaults-extra-file test/my.cnf","--query \"show databases\"","--file demo3.txt")
//        processBuilder.command("cp","demo2.txt","/src/resources")
                .directory(fi)
                .start();
        return null;
    }

}
