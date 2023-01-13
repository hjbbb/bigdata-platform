package cn.edu.xidian.bigdataplatform.ranger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

/**
 * @author suman.bn
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyResource {
    private List<String> values = Lists.newArrayList();

    private Boolean isExcludes;
    private Boolean isRecursive;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public Boolean getIsExcludes() {
        return isExcludes;
    }

    public void setIsExcludes(Boolean excludes) {
        isExcludes = excludes;
    }

    public Boolean getIsRecursive() {
        return isRecursive;
    }

    public void setIsRecursive(Boolean recursive) {
        isRecursive = recursive;
    }

    @Override
    public String toString() {
        return "PolicyResource{" +
                "values=" + values +
                ", isExcludes=" + isExcludes +
                ", isRecursive=" + isRecursive +
                '}';
    }
}
