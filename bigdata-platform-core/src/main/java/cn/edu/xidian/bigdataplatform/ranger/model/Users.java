package cn.edu.xidian.bigdataplatform.ranger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @author suman.bn
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Users {
    private int startIndex;
    private int pageSize;
    private int totalCount;
    private int resultSize;
    private String sortType;
    private String sortBy;
    private long queryTimeMS;
    private List<User> vXUsers;
    private List<Map<String, Object>> vXStrings;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(int resultSize) {
        this.resultSize = resultSize;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public long getQueryTimeMS() {
        return queryTimeMS;
    }

    public void setQueryTimeMS(long queryTimeMS) {
        this.queryTimeMS = queryTimeMS;
    }

    @JsonProperty("vXUsers")
    public List<User> getVXUsers() {
        return vXUsers;
    }


    public void setVXUsers(List<User> vXUsers) {
        this.vXUsers = vXUsers;
    }

    @JsonProperty("vXStrings")
    public List<Map<String, Object>> getVXStrings() {
        return vXStrings;
    }

    public void setVXStrings(List<Map<String, Object>> vXStrings) {
        this.vXStrings = vXStrings;
    }


//    @Override
//    public String toString() {
//        return "Users{" +
//                "startIndex=" + startIndex +
//                ", pageSize=" + pageSize +
//                ", totalCount=" + totalCount +
//                ", resultSize=" + resultSize +
//                ", sortType='" + sortType + '\'' +
//                ", sortBy='" + sortBy + '\'' +
//                ", queryTimeMS=" + queryTimeMS +
//                ", vXUsers=" + vXUsers +
//                ", vXStrings=" + vXStrings +
//                '}';
//    }
}
