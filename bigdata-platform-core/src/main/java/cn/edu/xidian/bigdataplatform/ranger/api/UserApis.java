package cn.edu.xidian.bigdataplatform.ranger.api;

import cn.edu.xidian.bigdataplatform.ranger.api.feign.UserFeignClient;
import cn.edu.xidian.bigdataplatform.ranger.model.User;
import cn.edu.xidian.bigdataplatform.ranger.model.Users;
import cn.edu.xidian.bigdataplatform.ranger.utils.RangerClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Param;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author suman.bn
 */
@Slf4j
@AllArgsConstructor
public class UserApis {

    private final UserFeignClient client;

    public User createUser(final User user) throws RangerClientException {
        return client.createUser(user);
    }

    public Users searchUsers(@Param("stringSearch") final String stringSearch) throws JsonProcessingException {
        return client.searchUsers(stringSearch);
    }

    public User getUserByName(@Param("name") String name) throws RangerClientException {
        return client.getUserByName(name);
    }

    public void setUserVisibility(Map<String, Integer> map) {
        client.setUserVisibility(map);
    }
}