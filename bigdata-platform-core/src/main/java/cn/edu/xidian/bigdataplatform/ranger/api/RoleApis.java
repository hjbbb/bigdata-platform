package cn.edu.xidian.bigdataplatform.ranger.api;

import cn.edu.xidian.bigdataplatform.ranger.api.feign.RoleFeignClient;
import cn.edu.xidian.bigdataplatform.ranger.model.Roles;
import cn.edu.xidian.bigdataplatform.ranger.model.Role;
import cn.edu.xidian.bigdataplatform.ranger.utils.RangerClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.Param;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@AllArgsConstructor
public class RoleApis {
    private final RoleFeignClient client;
    public Roles searchRoles(@Param("stringSearch") final String stringSearch) throws JsonProcessingException {
        return client.searchRoles(stringSearch);
    }

    public Role createRole(final Role role) throws RangerClientException {
        return client.createRole(role);
    }
    public Role getRoleById(@Param("roleId") final int roleId) {
        return client.getRoleById(roleId);
    }

    public void deleteRoleById(@Param("roleId") final int roleId) throws RangerClientException {
        client.deleteRoleById(roleId);
    }
    public Role updateRole(@Param("roleId") final int roleId, Role role) {
        return client.updateRole(roleId, role);
    }
}
