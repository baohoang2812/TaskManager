package baohg.taskmanager.baohg.responses;

import java.io.Serializable;
import java.util.List;

import baohg.taskmanager.baohg.dtos.UserDTO;

public class GetUserResponse implements Serializable {
    List<UserDTO> data;

    public void setData(List<UserDTO> data) {
        this.data = data;
    }

    public List<UserDTO> getData() {
        return data;
    }
}
