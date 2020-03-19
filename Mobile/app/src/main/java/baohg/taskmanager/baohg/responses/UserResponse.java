package baohg.taskmanager.baohg.responses;

import baohg.taskmanager.baohg.dtos.UserDTO;

public class UserResponse extends BaseResponse {
    UserDTO data;

    public UserDTO getData() {
        return data;
    }

    public void setData(UserDTO data) {
        this.data = data;
    }
}
