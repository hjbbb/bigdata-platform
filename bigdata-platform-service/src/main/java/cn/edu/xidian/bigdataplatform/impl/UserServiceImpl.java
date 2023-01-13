package cn.edu.xidian.bigdataplatform.impl;

import cn.edu.xidian.bigdataplatform.UserService;
import cn.edu.xidian.bigdataplatform.mybatis.entity.user.User;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-20
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.findUserByUsername(username);
        if (user != null) {
            if (!user.password.equals(password)) {
                return null;
            }
            return user;
        }
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public int updateUserPk(String username, String pk) {
        User user = userMapper.findUserByUsername(username);
        user.setPk(pk);
        user.setPkCreateTime(LocalDateTime.now());
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUserPk(String username) {
        User user = userMapper.findUserByUsername(username);
        user.setPk(null);
        user.setPkCreateTime(null);
        return userMapper.updateUser(user);
    }
}
