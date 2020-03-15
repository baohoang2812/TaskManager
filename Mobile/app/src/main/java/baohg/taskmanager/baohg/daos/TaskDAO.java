package baohg.taskmanager.baohg.daos;

import baohg.taskmanager.baohg.baohg.apis.APIClient;
import baohg.taskmanager.baohg.baohg.apis.TaskAPI;
import baohg.taskmanager.baohg.request.CreateTaskRequest;
import baohg.taskmanager.baohg.request.GetTaskRequest;
import baohg.taskmanager.baohg.responses.GetTaskResponse;
import baohg.taskmanager.baohg.responses.TaskResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class TaskDAO {
    private TaskAPI taskService;
    public TaskDAO() {
        taskService = APIClient.getTaskManagerClient().create(TaskAPI.class);
    }

    public void getAllTask(GetTaskRequest request, Callback<GetTaskResponse> callBack){
        taskService.getAllTask(request).enqueue(callBack);
    }

    public void createTask(CreateTaskRequest request, Callback<TaskResponse> callBack){
        taskService.createTask(request).enqueue(callBack);
    }

    public void getTaskDetail(@Path("id") int id, Callback<TaskResponse> callBack){
        taskService.getTaskById(id).enqueue(callBack);
    }

    public void deleteTask(@Path("id") int id, Callback<TaskResponse> callBack){
        taskService.deleteTask(id).enqueue(callBack);
    }

}
