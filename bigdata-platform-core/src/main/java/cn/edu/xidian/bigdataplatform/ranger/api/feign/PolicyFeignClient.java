package cn.edu.xidian.bigdataplatform.ranger.api.feign;

import cn.edu.xidian.bigdataplatform.ranger.model.Policy;
import cn.edu.xidian.bigdataplatform.ranger.utils.RangerClientException;
import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * @author suman.bn
 */
public interface PolicyFeignClient {

    /*
    Mapper to Ranger Policy APIs
     */
    @RequestLine("POST /service/public/v2/api/policy")
    Policy createPolicy(final Policy policy) throws RangerClientException;

    @RequestLine("GET /service/public/v2/api/service/{serviceName}/policy/{policyName}")
    Policy getPolicyByName(@Param("serviceName") final String serviceName,
                           @Param("policyName") final String policyName) throws RangerClientException;

    @RequestLine("GET /service/plugins/policies/{policyId}")
    Policy getPolicyById(@Param("policyId") final int policyId);

    @RequestLine("PUT /service/public/v2/api/policy/{policyId}")
    Policy updatePolicy(@Param("policyId") final int policyId,
                        final Policy policy) throws RangerClientException;

    @RequestLine("GET /service/public/v2/api/policy?serviceName={serviceName}&policyNamePartial={stringSearch}")
    List<Policy> searchPolicies(@Param("serviceName") final String serviceName,
                                @Param("stringSearch") final String stringSearch) throws RangerClientException;

    @RequestLine("GET /service/public/v2/api/service/{serviceName}/policy")
    List<Policy> getAllPoliciesByService(@Param("serviceName") final String serviceName) throws RangerClientException;

    @RequestLine("DELETE /service/plugins/policies/{policyId}")
    void deletePolicyById(@Param("policyId") final int policyId) throws RangerClientException;
}
