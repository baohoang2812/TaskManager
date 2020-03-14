package baohg.taskmanager.baohg.responses;

import java.util.List;

import baohg.taskmanager.baohg.dtos.TaskDTO;

public class GetTaskResponse extends BaseResponse{
    List<TaskDTO> data;

    public void setData(List<TaskDTO> data) {
        this.data = data;
    }


    public List<TaskDTO> getData() {
        return data;
    }
}
