package baohg.taskmanager.baohg.daos;

import baohg.taskmanager.baohg.baohg.apis.APIClient;
import baohg.taskmanager.baohg.baohg.apis.TaskAPI;
import baohg.taskmanager.baohg.request.GetTaskRequest;
import baohg.taskmanager.baohg.responses.TaskResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class TaskDAO {
    private TaskAPI taskService;
    public TaskDAO() {
        taskService = APIClient.getTaskManagerClient().create(TaskAPI.class);
    }

    public void getAllTask(GetTaskRequest request, Callback<TaskResponse> callBack){
        Call<TaskResponse> call = taskService.getAllTask(request);
        call.enqueue(callBack);
    }

}
