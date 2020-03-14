package baohg.taskmanager.baohg.responses;

import baohg.taskmanager.baohg.dtos.TaskDTO;

public class TaskResponse extends BaseResponse {
    TaskDTO data;

    public TaskDTO getData() {
        return data;
    }

    public void setData(TaskDTO data) {
        this.data = data;
    }
}
