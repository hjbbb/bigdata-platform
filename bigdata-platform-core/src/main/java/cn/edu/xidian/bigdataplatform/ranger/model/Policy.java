package cn.edu.xidian.bigdataplatform.ranger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @author suman.bn
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Policy {
    public Map<String, PolicyResource> getResources() {
        return resources;
    }

    public void setResources(Map<String, PolicyResource> resources) {
        this.resources = resources;
    }

    public List<PolicyItem> getPolicyItems() {
        return policyItems;
    }

    public void setPolicyItems(List<PolicyItem> policyItems) {
        this.policyItems = policyItems;
    }

    public List<PolicyItem> getDenyPolicyItems() {
        return denyPolicyItems;
    }

    public void setDenyPolicyItems(List<PolicyItem> denyPolicyItems) {
        this.denyPolicyItems = denyPolicyItems;
    }

    public List<PolicyItem> getAllowExceptions() {
        return allowExceptions;
    }

    public void setAllowExceptions(List<PolicyItem> allowExceptions) {
        this.allowExceptions = allowExceptions;
    }

    public List<PolicyItem> getDenyExceptions() {
        return denyExceptions;
    }

    public void setDenyExceptions(List<PolicyItem> denyExceptions) {
        this.denyExceptions = denyExceptions;
    }

    public List<Object> getDataMaskPolicyItems() {
        return dataMaskPolicyItems;
    }

    public void setDataMaskPolicyItems(List<Object> dataMaskPolicyItems) {
        this.dataMaskPolicyItems = dataMaskPolicyItems;
    }

    public List<Object> getRowFilterPolicyItems() {
        return rowFilterPolicyItems;
    }

    public void setRowFilterPolicyItems(List<Object> rowFilterPolicyItems) {
        this.rowFilterPolicyItems = rowFilterPolicyItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPolicyType() {
        return policyType;
    }

    public void setPolicyType(int policyType) {
        this.policyType = policyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsAuditEnabled() {
        return isAuditEnabled;
    }

    public void setIsAuditEnabled(boolean auditEnabled) {
        isAuditEnabled = auditEnabled;
    }

    Map<String, PolicyResource> resources;
    List<PolicyItem> policyItems = Lists.newArrayList();
    List<PolicyItem> denyPolicyItems = Lists.newArrayList();
    List<PolicyItem> allowExceptions = Lists.newArrayList();
    List<PolicyItem> denyExceptions = Lists.newArrayList();
    List<Object> dataMaskPolicyItems = Lists.newArrayList();
    List<Object> rowFilterPolicyItems = Lists.newArrayList();
    private int id;
    private String guid;
    private boolean isEnabled;
    private int version;
    private String service;
    private String name;
    private int policyType;
    private String description;
    private boolean isAuditEnabled;

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", guid='" + guid + '\'' +
                ", isEnabled=" + isEnabled +
                ", version=" + version +
                ", service='" + service + '\'' +
                ", name='" + name + '\'' +
                ", policyType=" + policyType +
                ", description='" + description + '\'' +
                ", isAuditEnabled=" + isAuditEnabled +
                ", ResourcesObject=" + resources +
                ", policyItems=" + policyItems +
                ", denyPolicyItems=" + denyPolicyItems +
                ", allowExceptions=" + allowExceptions +
                ", denyExceptions=" + denyExceptions +
                ", dataMaskPolicyItems=" + dataMaskPolicyItems +
                ", rowFilterPolicyItems=" + rowFilterPolicyItems +
                '}';
    }
}
