package baohg.taskmanager.baohg.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateUserRequest implements Serializable {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("fullname")
    String fullName;
    @SerializedName("email")
    String email;
    @SerializedName("phone")
    String phone;
    @SerializedName("groupId")
    int groupId;

    public CreateUserRequest() {
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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
