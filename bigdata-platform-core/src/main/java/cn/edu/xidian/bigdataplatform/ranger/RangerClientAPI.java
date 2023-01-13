package cn.edu.xidian.bigdataplatform.ranger;

import cn.edu.xidian.bigdataplatform.ranger.model.Policy;
import cn.edu.xidian.bigdataplatform.ranger.model.Role;
import cn.edu.xidian.bigdataplatform.ranger.model.Roles;
import cn.edu.xidian.bigdataplatform.ranger.model.Users;
import cn.edu.xidian.bigdataplatform.ranger.utils.RangerClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RangerClientAPI {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RangerClientAPI.class);

    @Autowired
    private RangerClient client;

    public List<Policy> getStoreAllPolicies() {
        return client.getPolicies().getAllPoliciesByService("hadoopdev");
    }

    public Policy getStorePolicyDetail(int policyId) {
        return client.getPolicies().getPolicyById(policyId);
    }

    public Policy createPolicy(Policy policy) throws  RangerClientException{
        return client.getPolicies().createPolicy(policy);
    }
    public Users getAllUsers() throws JsonProcessingException {
        return client.getUsers().searchUsers("");
    }
    public Roles getAllRoles() throws JsonProcessingException {
        return client.getRoles().searchRoles("");
    }
    public Role getRoleDetail(int roleId) {
        return client.getRoles().getRoleById(roleId);
    }

    public Role createRole(Role role) throws RangerClientException {
        return client.getRoles().createRole(role);
    }
    public Role updateRole(int roleId, Role role) throws RangerClientException {
        return client.getRoles().updateRole(roleId, role);
    }

    public void deleteRole(int roleId) throws RangerClientException {
        client.getRoles().deleteRoleById(roleId);
    }
    public void deletePolicy(int policyId) throws RangerClientException{
        client.getPolicies().deletePolicyById(policyId);
    }

    public Policy updatePolicy(int policyId, Policy policy) throws  RangerClientException{
        return client.getPolicies().updatePolicy(policyId, policy);
    }
}
