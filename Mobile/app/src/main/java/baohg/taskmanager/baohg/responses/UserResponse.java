package baohg.taskmanager.baohg.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import baohg.taskmanager.baohg.dtos.UserDTO;

public class UserResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    UserDTO data;

    public UserDTO getData() {
        return data;
    }

    public void setData(UserDTO data) {
        this.data = data;
    }
}
