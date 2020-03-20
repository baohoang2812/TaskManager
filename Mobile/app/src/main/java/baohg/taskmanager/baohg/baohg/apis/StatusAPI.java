package baohg.taskmanager.baohg.baohg.apis;

import baohg.taskmanager.baohg.responses.StatusResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StatusAPI {
    @GET("api/Status")
    Call<StatusResponse> getAllStatus();
}
