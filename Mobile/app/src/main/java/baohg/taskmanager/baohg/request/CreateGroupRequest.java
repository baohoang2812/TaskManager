package baohg.taskmanager.baohg.request;

import com.google.gson.annotations.SerializedName;

public class CreateGroupRequest {
    @SerializedName("managerId")
    int managerId;
    @SerializedName("userId")
    int userId;

    public CreateGroupRequest(int managerId, int userId) {
        this.managerId = managerId;
        this.userId = userId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
