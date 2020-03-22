package baohg.taskmanager.baohg.request;

import java.io.Serializable;

public class UpdateUserRequest implements Serializable {
    String fullName;
    String email;
    String phone;
    int roleId;
    String roleName;
    Integer groupId;

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getRoleId() {
        return roleId;
    }
}
