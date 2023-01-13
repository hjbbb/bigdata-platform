package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.ApiManageService;
import cn.edu.xidian.bigdataplatform.base.Result;
import cn.edu.xidian.bigdataplatform.base.ResultCode;
import cn.edu.xidian.bigdataplatform.bean.userApiFunc;
import cn.edu.xidian.bigdataplatform.mybatis.entity.apimanage.UserApiItem;
import cn.edu.xidian.bigdataplatform.mybatis.entity.mysqlbackup.BackupItem;
import cn.edu.xidian.bigdataplatform.mybatis.mapper.ApiManagerMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ApiManagerController {

    @Autowired
    private ApiManageService apiManageService;
    @GetMapping("/api/api-manager/check-url/")
    public Result checkUrl(@RequestParam(name = "url") String url) {
        UserApiItem userApiItem = apiManageService.getApiByUrl(url);
        return Result.success(!(userApiItem == null));
    }

    @GetMapping("/api/api-manager/user-apis/")
    public Result getUserApis(@RequestParam String uuid) {
        try {
            List<UserApiItem> userApiItems = apiManageService.getUserApis(uuid);
            return Result.success(userApiItems);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed(ResultCode.FAILED);
        }
    }

    @GetMapping("/api/api-manager/user-apis/{item-id}")
    public Result getUserApiDetail(@PathVariable(value = "item-id") int itemId) {
        try {
            UserApiItem userApiItem = apiManageService.getApiById(itemId);
            return Result.success(userApiItem);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed(ResultCode.FAILED);
        }
    }

    @PostMapping("/api/api-manager/user-apis/")
    public Result newApiItem(@RequestBody UserApiItem userApiItem) {
        try{
            int line = apiManageService.insertNewApiItem(userApiItem);
            if(line == 1) {
                return Result.success(true);
            } else {
                return Result.failed(ResultCode.FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed(ResultCode.FAILED);
        }
    }
    @PutMapping("/api/api-manager/user-apis/{item-id}")
    public Result updateUserApiItem(@PathVariable(value="item-id") int itemId, @RequestBody UserApiItem userApiItem) {
        try{
            boolean ret = apiManageService.updateApiItem(userApiItem);
            return Result.success(ret);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed(ResultCode.FAILED);
        }
    }
    @DeleteMapping("/api/api-manager/user-apis/{item-id}")
    public Result deleteUserApiItem(@PathVariable(value="item-id") int itemId) {
        boolean ret = apiManageService.deleteItemById(itemId);
        return Result.success(ret);
    }

    @GetMapping("/userApi/{url}")
    public Result handleUserApi(@PathVariable(value="url") String url, @RequestParam(name="pageNo") int pageNo, @RequestParam(name="pageSize") int pageSize) {
        UserApiItem userApiItem = apiManageService.getApiByUrl(url);
        if(userApiItem == null) {
            JSONObject result = new JSONObject();
            result.put("code", 10001);
            result.put("data", null);
            result.put("msg", "操作失败");
            return Result.success(result);
        }
		List<Object> res = apiManageService.showData(userApiItem, pageNo, pageSize);
        JSONObject result = new JSONObject();
        result.put("code", 0);
        result.put("data", res);
        result.put("msg", "请求成功");
        return Result.success(result);
    }
}
