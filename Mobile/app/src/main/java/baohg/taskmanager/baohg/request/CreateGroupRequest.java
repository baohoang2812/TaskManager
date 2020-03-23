package baohg.taskmanager.baohg.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateGroupRequest implements Serializable {
    @SerializedName("userId")
    int userId;
    @SerializedName("description")
    String description;
    @SerializedName("createdTime")
    String createdTime;
    @SerializedName("name")
    String name;

    public CreateGroupRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedTime() {
        return createdTime;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
