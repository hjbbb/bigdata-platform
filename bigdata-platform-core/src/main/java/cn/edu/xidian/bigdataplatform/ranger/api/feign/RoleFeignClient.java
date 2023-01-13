package cn.edu.xidian.bigdataplatform.ranger.api.feign;

import cn.edu.xidian.bigdataplatform.ranger.model.Roles;
import cn.edu.xidian.bigdataplatform.ranger.model.Role;
import cn.edu.xidian.bigdataplatform.ranger.utils.RangerClientException;
import feign.Param;
import feign.RequestLine;

public interface RoleFeignClient {
    @RequestLine("GET /service/roles/roles?name={stringSearch}&isVisible=1")
    Roles searchRoles(@Param("stringSearch") final String stringSearch);

    @RequestLine("GET /service/roles/roles/{roleId}")
    Role getRoleById(@Param("roleId") final int roleId);

    @RequestLine("PUT /service/roles/roles/{roleId}")
    Role updateRole(@Param("roleId") final int roleId, Role role) throws RangerClientException;

    @RequestLine("DELETE /service/roles/roles/{roleId}")
    void deleteRoleById(@Param("roleId") final int roleId) throws RangerClientException;

    @RequestLine("POST /service/roles/roles")
    Role createRole(final Role role) throws RangerClientException;
}
