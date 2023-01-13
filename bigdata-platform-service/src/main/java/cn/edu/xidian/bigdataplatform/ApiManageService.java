package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.mybatis.entity.apimanage.UserApiItem;

import java.util.List;

public interface ApiManageService {
    UserApiItem getApiByUrl(String url);

    UserApiItem getApiById(int itemId);

    int insertNewApiItem(UserApiItem userApiItem);
    boolean updateApiItem(UserApiItem userApiItem);
    List<UserApiItem> getUserApis(String uuid);

    boolean deleteItemById(int id);

    List<Object> showData(UserApiItem userApiItem, int pageNo, int pageSize);
}
