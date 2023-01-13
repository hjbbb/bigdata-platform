package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.search.FreightSource;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-24
 * @description:
 */
@Mapper
public interface SearchMapper {
    List<FreightSource> queryFreightByDepartAndDestination(
            @Param("dbName") String dbName,
            @Param("departParam") String departParam,
            @Param("destinationParam") String destinationParam,
            @Param("distanceParam") String distanceParam,
            @Param("freightDescParam") String freightDescParam,
            @Param("transportTypeParam") String transportTypeParam);

    List<FreightSource> queryLimited();
}
