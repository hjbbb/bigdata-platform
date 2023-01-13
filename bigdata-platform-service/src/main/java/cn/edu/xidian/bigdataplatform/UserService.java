package cn.edu.xidian.bigdataplatform;

import cn.edu.xidian.bigdataplatform.mybatis.entity.user.User;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-20
 * @description: 用户相关服务
 */
public interface UserService {
    User login(String username, String password);
    User findUserByUsername(String username);
    int updateUserPk(String username, String pk);
    int deleteUserPk(String username);
}
