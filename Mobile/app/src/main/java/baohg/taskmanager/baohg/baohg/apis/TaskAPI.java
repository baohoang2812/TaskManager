package baohg.taskmanager.baohg.baohg.apis;

import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.request.GetTaskRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface TaskAPI {
    @GET("/api/Tasks/all")
    Call<TaskDTO> GetAll(@Body GetTaskRequest request);
}
