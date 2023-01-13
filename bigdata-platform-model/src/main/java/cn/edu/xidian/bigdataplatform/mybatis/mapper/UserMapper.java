package cn.edu.xidian.bigdataplatform.mybatis.mapper;

import cn.edu.xidian.bigdataplatform.mybatis.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-20
 * @description:
 */
@Mapper
public interface UserMapper {
    User findUserByUsername(String username);

    int updateUser(User user);
}
