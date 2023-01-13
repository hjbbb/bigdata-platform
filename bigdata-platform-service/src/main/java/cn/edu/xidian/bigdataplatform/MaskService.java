package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.bean.MaskingTaskPageResult;
import cn.edu.xidian.bigdataplatform.mybatis.entity.Maskpojo;


import java.io.IOException;
import java.util.List;

public interface MaskService {
    List<Maskpojo> searchAllMask();

    MaskingTaskPageResult getPagedMaskTask(int pageNum, int pageSize);

    Maskpojo addNewMask(Maskpojo maskpojo);

    Boolean runMask(String s,String t,String uuid,Integer maskway,Integer savetype)throws IOException;
}
