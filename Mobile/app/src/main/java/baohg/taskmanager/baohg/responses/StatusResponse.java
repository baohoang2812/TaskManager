package baohg.taskmanager.baohg.responses;

import java.util.List;

import baohg.taskmanager.baohg.dtos.StatusDTO;

public class StatusResponse extends BaseResponse{
    List<StatusDTO> data;

    public List<StatusDTO> getStatusList() {
        return data;
    }

    public void setStatusList(List<StatusDTO> statusList) {
        this.data = statusList;
    }
}
