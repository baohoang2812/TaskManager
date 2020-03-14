package baohg.taskmanager.baohg.baohg.apis;

import baohg.taskmanager.baohg.request.CreateGroupRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GroupAPI {
    @POST("api/Groups")
    Call<Void> createGroup(@Body CreateGroupRequest request);
}
