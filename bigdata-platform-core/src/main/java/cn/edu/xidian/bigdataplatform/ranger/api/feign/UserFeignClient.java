package cn.edu.xidian.bigdataplatform.ranger.api.feign;

import cn.edu.xidian.bigdataplatform.ranger.model.User;
import cn.edu.xidian.bigdataplatform.ranger.model.Users;
import cn.edu.xidian.bigdataplatform.ranger.utils.RangerClientException;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

/**
 * @author suman.bn
 */
public interface UserFeignClient {

    /*
    Mapper to Ranger User APIs
     */
    @RequestLine("POST /service/xusers/secure/users")
    User createUser(final User user) throws RangerClientException;

    @RequestLine("GET /service/xusers/lookup/users?name={stringSearch}&isVisible=1")
    Users searchUsers(@Param("stringSearch") final String stringSearch);


    @RequestLine("GET /service/xusers/users/userName/{name}")
    User getUserByName(@Param("name") String name) throws RangerClientException;

    @RequestLine("PUT /service/xusers/secure/users/visibility")
    void setUserVisibility(Map<String, Integer> map);
}
