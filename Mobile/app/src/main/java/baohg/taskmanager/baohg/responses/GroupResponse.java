package baohg.taskmanager.baohg.responses;

import baohg.taskmanager.baohg.dtos.GroupDTO;

public class GroupResponse extends BaseResponse{
    GroupDTO data;

    public GroupDTO getData() {
        return data;
    }

    public void setData(GroupDTO data) {
        this.data = data;
    }
}
