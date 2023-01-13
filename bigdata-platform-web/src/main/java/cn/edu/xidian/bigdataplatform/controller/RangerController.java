package cn.edu.xidian.bigdataplatform.controller;

import cn.edu.xidian.bigdataplatform.ranger.RangerClientAPI;
import cn.edu.xidian.bigdataplatform.ranger.model.Policy;
import cn.edu.xidian.bigdataplatform.ranger.model.Users;
import cn.edu.xidian.bigdataplatform.ranger.model.Roles;
import cn.edu.xidian.bigdataplatform.ranger.model.Role;
import cn.edu.xidian.bigdataplatform.ranger.utils.RangerClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.xidian.bigdataplatform.base.Result;

import java.util.List;

@RestController
public class RangerController {
    @Autowired
    private RangerClientAPI rangerClientAPI;
    private static Logger logger = LoggerFactory.getLogger(RangerController.class);

    @GetMapping("/api/auth/store/get-policies")
    public Result getStorePolicies() throws JsonProcessingException {
        List<Policy>  policies = rangerClientAPI.getStoreAllPolicies();
        return Result.success(policies);
    }

    @GetMapping("/api/auth/store/policy-detail")
    public Result getStorePolicyDetail(@RequestParam(name="policyId") int policyId) {
        Policy policy = rangerClientAPI.getStorePolicyDetail(policyId);
        return Result.success(policy);
    }

    @DeleteMapping("/api/auth/store/policy/{policy-id}")
    public Result deleteStorePolicy(@PathVariable(value="policy-id") int policyId) {
        try {
            rangerClientAPI.deletePolicy(policyId);
            return Result.success("Delete policy " + policyId + " succeed");
        } catch (RangerClientException e) {
            return Result.failed(e.getMessage());
        }
    }
    @PutMapping("/api/auth/store/policy/{policy-id}")
    public Result updatePolicy(@PathVariable(value="policy-id") int policyId, @RequestBody Policy policy) {
        try {
            Policy  res = rangerClientAPI.updatePolicy(policyId, policy);
            return Result.success(res);
        } catch (RangerClientException e) {
            return Result.failed(e.getMessage());
        }
    }

    @PostMapping("/api/auth/store/create-policy")
    public Result createStorePolicy(@RequestBody Policy policy) {
        try {
            Policy  res = rangerClientAPI.createPolicy(policy);
            return Result.success(res);
        } catch (RangerClientException e) {
            return Result.failed(e.getMessage());
        }
    }

    @GetMapping("/api/auth/users")
    public Result getAllUsers() throws JsonProcessingException {
        Users users = rangerClientAPI.getAllUsers();
        return Result.success(users);
    }

    @GetMapping("/api/auth/roles")
    public Result getAllRoles() throws JsonProcessingException {
        Roles roles = rangerClientAPI.getAllRoles();
        return Result.success(roles);
    }

    @PostMapping("/api/auth/roles/")
    public Result createRole(@RequestBody Role role) {
        try {
            Role res = rangerClientAPI.createRole(role);
            return Result.success(res);
        } catch (RangerClientException e) {
            return Result.failed(e.getMessage());
        }
    }

    @GetMapping("/api/auth/roles/{roleId}")
    public Result getRoleDetail(@PathVariable(value = "roleId") int roleId) {
        Role role = rangerClientAPI.getRoleDetail(roleId);
        return Result.success(role);
    }

    @PutMapping("/api/auth/roles/{roleId}")
    public Result updateRole(@PathVariable(value = "roleId") int roleId, @RequestBody Role role) {
        try {
            Role res = rangerClientAPI.updateRole(roleId, role);
            return Result.success(res);
        } catch (RangerClientException e) {
            return Result.failed(e.getMessage());
        }
    }

    @DeleteMapping("/api/auth/roles/{role-id}")
    public Result deleteRole(@PathVariable(value="role-id") int roleId) {
        try {
            rangerClientAPI.deleteRole(roleId);
            return Result.success("Delete role " + roleId + " succeed");
        } catch (RangerClientException e) {
            return Result.failed(e.getMessage());
        }
    }
}
