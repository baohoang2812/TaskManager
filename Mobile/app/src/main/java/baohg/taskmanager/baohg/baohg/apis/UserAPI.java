package baohg.taskmanager.baohg.baohg.apis;

import baohg.taskmanager.baohg.request.LoginRequest;
import baohg.taskmanager.baohg.dtos.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("api/Users/login")
    Call<UserDTO> login(@Body LoginRequest request);
}
