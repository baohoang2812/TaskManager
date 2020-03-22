package baohg.taskmanager.baohg.daos;

import java.io.Serializable;

import baohg.taskmanager.baohg.apis.APIClient;
import baohg.taskmanager.baohg.apis.StatusAPI;
import baohg.taskmanager.baohg.responses.StatusResponse;
import retrofit2.Callback;

public class StatusDAO implements Serializable {
    StatusAPI statusAPI;
    public StatusDAO() {
        statusAPI = APIClient.getTaskManagerClient().create(StatusAPI.class);
    }
    public void getAllStatus(String name, Callback<StatusResponse> callBack){
        statusAPI.getAllStatus(name).enqueue(callBack);
    }
}
