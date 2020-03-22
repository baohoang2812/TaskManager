package baohg.taskmanager.baohg.daos;

import baohg.taskmanager.baohg.apis.APIClient;
import baohg.taskmanager.baohg.apis.TaskAPI;
import baohg.taskmanager.baohg.request.CreateTaskRequest;
import baohg.taskmanager.baohg.request.GetTaskRequest;
import baohg.taskmanager.baohg.request.UpdateTaskRequest;
import baohg.taskmanager.baohg.responses.GetTaskResponse;
import baohg.taskmanager.baohg.responses.TaskResponse;
import retrofit2.Callback;

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

    public void getTaskDetail(int id, Callback<TaskResponse> callBack){
        taskService.getTaskById(id).enqueue(callBack);
    }

    public void deleteTask(int id, Callback<TaskResponse> callBack){
        taskService.deleteTask(id).enqueue(callBack);
    }

    public void updateTask(int id, UpdateTaskRequest request, Callback<TaskResponse> callBack){
        taskService.updateTask(id, request).enqueue(callBack);
    }

}
