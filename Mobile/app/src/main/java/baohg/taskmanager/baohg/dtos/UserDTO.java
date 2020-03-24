package baohg.taskmanager.baohg.dtos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class UserDTO implements Serializable {
    @SerializedName("userId")
    int userId;
    @SerializedName("roleId")
    int roleId;
    @SerializedName("fullName")
    String fullName;
    @SerializedName("email")
    String email;
    @SerializedName("groupId")
    Integer groupId;
    @SerializedName("roleName")
    String roleName;
    @SerializedName("phone")
    String phoneNumber;
    @SerializedName("username")
    String userName;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return fullName;
    }
}
