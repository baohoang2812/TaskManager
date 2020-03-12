package baohg.taskmanager.baohg.daos;

import baohg.taskmanager.baohg.baohg.apis.APIClient;
import baohg.taskmanager.baohg.baohg.apis.UserAPI;
import baohg.taskmanager.baohg.request.LoginRequest;
import baohg.taskmanager.baohg.dtos.UserDTO;
import retrofit2.Call;
import retrofit2.Callback;

public class UserDAO {

    public boolean checkLogin(String username, String password, Callback<UserDTO> callBack){
        UserAPI apiService = APIClient.getTaskManagerClient().create(UserAPI.class);
        LoginRequest request = new LoginRequest(username, password);
        Call<UserDTO> call =  apiService.login(request);
        call.enqueue(callBack);
        return false;
    }
}
