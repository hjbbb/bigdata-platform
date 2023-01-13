package cn.edu.xidian.bigdataplatform.ranger.api;

import cn.edu.xidian.bigdataplatform.ranger.api.feign.PolicyFeignClient;
import cn.edu.xidian.bigdataplatform.ranger.model.Policy;
import cn.edu.xidian.bigdataplatform.ranger.utils.RangerClientException;
import feign.Param;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author suman.bn
 */
@Slf4j
@AllArgsConstructor
public class PolicyApis {

    private final PolicyFeignClient client;

    public Policy createPolicy(final Policy policy) throws RangerClientException {
        return client.createPolicy(policy);
    }

    public Policy getPolicyByName(@Param("serviceName") final String serviceName,
                                  @Param("policyName") final String policyName) throws RangerClientException {
        return client.getPolicyByName(serviceName, policyName);
    }

    public Policy updatePolicy(@Param("policyId") final int policyId, final Policy policy) throws RangerClientException {
        return client.updatePolicy(policyId, policy);
    }

    public List<Policy> searchPolicies(@Param("serviceName") final String serviceName,
                                       @Param("stringSearch") final String stringSearch) throws RangerClientException {
        return client.searchPolicies(serviceName, stringSearch);
    }

    public List<Policy> getAllPoliciesByService(@Param("serviceName") final String serviceName) throws RangerClientException {
        return client.getAllPoliciesByService(serviceName);
    }

    public Policy getPolicyById(@Param("policyId") final int policyId) throws RangerClientException {
        return client.getPolicyById(policyId);
    }

    public void deletePolicyById(@Param("policyId") final int policyId) throws RangerClientException{
        client.deletePolicyById(policyId);
    }
}
