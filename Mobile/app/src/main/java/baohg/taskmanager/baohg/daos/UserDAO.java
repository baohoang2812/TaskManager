package baohg.taskmanager.baohg.daos;

import baohg.taskmanager.baohg.baohg.apis.APIClient;
import baohg.taskmanager.baohg.baohg.apis.UserAPI;
import baohg.taskmanager.baohg.request.LoginRequest;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.responses.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class UserDAO {
    UserAPI userAPI;

    public UserDAO() {
        userAPI = APIClient.getTaskManagerClient().create(UserAPI.class);
    }

    public void checkLogin(LoginRequest request, Callback<UserResponse> callBack){
        userAPI.login(request).enqueue(callBack);
    }
    public void getUserProfile(int userId, Callback<UserResponse> callBack){
        userAPI.getUserById(userId).enqueue(callBack);
    }
}
