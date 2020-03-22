package baohg.taskmanager.baohg.apis;

import baohg.taskmanager.baohg.responses.StatusResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StatusAPI {
    @GET("api/Status")
    Call<StatusResponse> getAllStatus(@Query("name") String name);
}
