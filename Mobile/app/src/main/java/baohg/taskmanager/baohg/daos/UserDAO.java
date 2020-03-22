package baohg.taskmanager.baohg.daos;

import baohg.taskmanager.baohg.apis.APIClient;
import baohg.taskmanager.baohg.apis.UserAPI;
import baohg.taskmanager.baohg.request.CreateUserRequest;
import baohg.taskmanager.baohg.request.GetUserRequest;
import baohg.taskmanager.baohg.request.LoginRequest;
import baohg.taskmanager.baohg.request.UpdateUserRequest;
import baohg.taskmanager.baohg.responses.GetUserResponse;
import baohg.taskmanager.baohg.responses.UserResponse;
import retrofit2.Callback;

public class UserDAO {
    UserAPI userAPI;

    public UserDAO() {
        userAPI = APIClient.getTaskManagerClient().create(UserAPI.class);
    }

    public void checkLogin(LoginRequest request, Callback<UserResponse> callBack) {
        userAPI.login(request).enqueue(callBack);
    }

    public void getUserProfile(int userId, Callback<UserResponse> callBack) {
        userAPI.getUserById(userId).enqueue(callBack);
    }

    public void getAllUser(GetUserRequest request, Callback<GetUserResponse> callBack) {
        userAPI.getAllUser(request).enqueue(callBack);
    }

    public void updateUser(int userId, UpdateUserRequest request, Callback<UserResponse> callBack) {
        userAPI.updateUser(userId, request).enqueue(callBack);
    }
    public void createUser(CreateUserRequest request, Callback<UserResponse> callback){
        userAPI.createuser(request).enqueue(callback);
    }
    public void removeUser(int userId, Callback<UserResponse> callback){
        userAPI.removeUser(userId).enqueue(callback);
    }
}
