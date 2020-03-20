package baohg.taskmanager.baohg.daos;

import java.io.Serializable;

import baohg.taskmanager.baohg.baohg.apis.APIClient;
import baohg.taskmanager.baohg.baohg.apis.StatusAPI;
import baohg.taskmanager.baohg.responses.StatusResponse;
import retrofit2.Callback;

public class StatusDAO implements Serializable {
    StatusAPI statusAPI;
    public StatusDAO() {
        statusAPI = APIClient.getTaskManagerClient().create(StatusAPI.class);
    }
    public void getAllStatus(Callback<StatusResponse> callBack){
        statusAPI.getAllStatus().enqueue(callBack);
    }
}
