package baohg.taskmanager.baohg.baohg.apis;

import baohg.taskmanager.baohg.request.CreateUserRequest;
import baohg.taskmanager.baohg.request.LoginRequest;
import baohg.taskmanager.baohg.dtos.UserDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {
    @POST("api/Users/login")
    Call<UserDTO> login(@Body LoginRequest request);

    @POST("api/Users")
    Call<ResponseBody> createuser(@Body CreateUserRequest request);

    @PUT("api/Users/{id}")
    Call<ResponseBody> updateUser(@Path ("id") int id, @Body CreateUserRequest request);

    @DELETE("api/Users/{id}")
    Call<ResponseBody> removeUser(@Path("id") int id);

    @GET("api/Users/{id}")
    Call<UserDTO> getUserById(@Path("id") int id);
}
