package baohg.taskmanager.baohg.responses;

import java.util.List;

import baohg.taskmanager.baohg.dtos.GroupDTO;

public class GetGroupResponse extends BaseResponse{
    List<GroupDTO> data;

    public List<GroupDTO> getData() {
        return data;
    }

    public void setData(List<GroupDTO> data) {
        this.data = data;
    }
}
