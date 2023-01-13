package cn.edu.xidian.bigdataplatform.ranger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * @author suman.bn
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String createDate;
    private String updateDate;
    private String owner;
    private String updatedBy;
    private String name;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String description;

    private List<Number> groupIdList;
    private List<String> groupNameList;
    private int status;
    private int isVisible;
    private int userSource;
    private List<String> userRoleList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Number> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<Number> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public List<String> getGroupNameList() {
        return groupNameList;
    }

    public void setGroupNameList(List<String> groupNameList) {
        this.groupNameList = groupNameList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getUserSource() {
        return userSource;
    }

    public void setUserSource(int userSource) {
        this.userSource = userSource;
    }

    public List<String> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<String> userRoleList) {
        this.userRoleList = userRoleList;
    }



//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", createDate='" + createDate + '\'' +
//                ", updateDate='" + updateDate + '\'' +
//                ", owner='" + owner + '\'' +
//                ", updatedBy='" + updatedBy + '\'' +
//                ", name='" + name + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", emailAddress='" + emailAddress + '\'' +
//                ", password='" + password + '\'' +
//                ", description='" + description + '\'' +
//                ", groupIdList='" + groupIdList + '\'' +
//                ", groupNameList='" + groupNameList + '\'' +
//                ", status=" + status +
//                ", isVisible=" + isVisible +
//                ", userSource=" + userSource +
//                ", userRoleList=" + userRoleList +
//                '}';
//    }
}
