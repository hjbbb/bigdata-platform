package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.PSpace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-10
 * @description:
 */
@Repository
public interface PSpaceMapper {
    int insertPSpaceSource();

    List<PSpace> queryAllPSpaceSources();

    int updatePSpace(@Param("url") String url, @Param("user") String user, @Param("password") String password);

    PSpace findPSpaceByDataType(String dataType);
}
