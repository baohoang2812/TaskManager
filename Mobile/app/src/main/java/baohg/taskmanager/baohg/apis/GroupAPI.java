package baohg.taskmanager.baohg.apis;

import baohg.taskmanager.baohg.request.CreateGroupRequest;
import baohg.taskmanager.baohg.responses.GetGroupResponse;
import baohg.taskmanager.baohg.responses.GroupResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroupAPI {
    @POST("api/Groups")
    Call<GroupResponse> createGroup(@Body CreateGroupRequest request);

    @GET("api/Groups/{id}")
    Call<GroupResponse> getGroup(@Path("id") int id);

    @GET("api/Groups")
    Call<GetGroupResponse> getAllGroup();
}
