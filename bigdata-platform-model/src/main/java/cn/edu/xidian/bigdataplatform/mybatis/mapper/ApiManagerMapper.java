package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.apimanage.UserApiItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiManagerMapper {
    UserApiItem getApiByUrl(String url);

    UserApiItem getApiById(int itemId);

    int insertNewApiItem(UserApiItem userApiItem);
    boolean updateApiItem(UserApiItem userApiItem);
    List<UserApiItem> getUserApis(String uuid);

    boolean deleteItemById(int id);
}
