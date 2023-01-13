package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.AccessLogService;
import cn.edu.xidian.bigdataplatform.UserService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.base.ResultCode;
import cn.edu.xidian.bigdataplatform.mybatis.entity.AccessLog;
import cn.edu.xidian.bigdataplatform.mybatis.entity.Maskpojo;
import cn.edu.xidian.bigdataplatform.mybatis.entity.user.User;
import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.DataSource;
import cn.edu.xidian.bigdataplatform.utils.UUIDUtil;
import cn.edu.xidian.bigdataplatform.vo.LoginVO;
import cn.edu.xidian.bigdataplatform.vo.LogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-20
 * @description: 维护用户相关接口
 */
@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    public UserService userService;

    @Autowired
    private AccessLogService accessLogService;


    @PostMapping(value = "/api/login")
    public Result login(@RequestBody LoginVO userInfo) {
        System.out.println("###############访问/api/login");
        User loginSuccess = userService.login(userInfo.username, userInfo.password);
        if (loginSuccess == null) {
            logger.debug(userInfo.username + " 登录失败");
            return Result.failed(ResultCode.LOGIN_FAILED);
        }
        LoginVO vo = new LoginVO();
        vo.uuid = loginSuccess.uuid;
        vo.username = loginSuccess.username;
        vo.token = UUIDUtil.genUUID();
        String username = loginSuccess.username;
        String operate = "用户登录";
        accessLogService.addAccesslog(username, operate);
        return Result.success(vo);
    }


    @PostMapping("/api/logout")
    public Result logout(@RequestBody LoginVO userInfo) {
        String username = userInfo.username;
        String operate = "用户注销";
        accessLogService.addAccesslog(username, operate);
        LoginVO vo = new LoginVO();
        vo.username = userInfo.username;
        return Result.success(vo);
    }
}


