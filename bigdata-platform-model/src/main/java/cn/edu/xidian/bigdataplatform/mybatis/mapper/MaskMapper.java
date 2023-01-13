package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.Maskpojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaskMapper {
    List<Maskpojo> searchAllMask();

    int insertNewMask(Maskpojo newMask);


}
