package baohg.taskmanager.baohg.apis;

import baohg.taskmanager.baohg.request.CreateUserRequest;
import baohg.taskmanager.baohg.request.GetUserRequest;
import baohg.taskmanager.baohg.request.LoginRequest;
import baohg.taskmanager.baohg.request.UpdateUserRequest;
import baohg.taskmanager.baohg.responses.GetUserResponse;
import baohg.taskmanager.baohg.responses.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {
    @POST("api/Users/login")
    Call<UserResponse> login(@Body LoginRequest request);

    @POST("api/Users")
    Call<UserResponse> createUser(@Body CreateUserRequest request);

    @PUT("api/Users/{id}")
    Call<UserResponse> updateUser(@Path ("id") int id, @Body UpdateUserRequest request);

    @DELETE("api/Users/{id}")
    Call<UserResponse> removeUser(@Path("id") int id);

    @GET("api/Users/{id}")
    Call<UserResponse> getUserById(@Path("id") int id);

    @POST("api/Users/all")
    Call<GetUserResponse> getAllUser(@Body GetUserRequest request);
}
