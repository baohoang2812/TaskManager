package baohg.taskmanager.baohg.request;

import com.google.gson.annotations.SerializedName;

public class CreateUserRequest {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("roleId")
    int roleId;
    @SerializedName("fullname")
    String fullName;
    @SerializedName("email")
    String email;
    @SerializedName("groupId")
    int groupId;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String password, int roleId, String fullName, String email, int groupId) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.fullName = fullName;
        this.email = email;
        this.groupId = groupId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setFullname(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
