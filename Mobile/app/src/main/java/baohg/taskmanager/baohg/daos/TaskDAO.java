package baohg.taskmanager.baohg.daos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import baohg.taskmanager.baohg.apis.APIClient;
import baohg.taskmanager.baohg.apis.TaskAPI;
import baohg.taskmanager.baohg.request.CreateTaskRequest;
import baohg.taskmanager.baohg.request.GetTaskRequest;
import baohg.taskmanager.baohg.request.UpdateTaskRequest;
import baohg.taskmanager.baohg.responses.GetTaskResponse;
import baohg.taskmanager.baohg.responses.TaskResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.http.Multipart;

public class TaskDAO {
    private TaskAPI taskService;

    public TaskDAO() {
        taskService = APIClient.getTaskManagerClient().create(TaskAPI.class);
    }

    public void getAllTask(GetTaskRequest request, Callback<GetTaskResponse> callBack) {
        taskService.getAllTask(request).enqueue(callBack);
    }

    public void createTask(CreateTaskRequest request, Callback<TaskResponse> callBack) {
        taskService.createTask(request).enqueue(callBack);
    }

    public void getTaskDetail(int id, Callback<TaskResponse> callBack) {
        taskService.getTaskById(id).enqueue(callBack);
    }

    public void deleteTask(int id, Callback<TaskResponse> callBack) {
        taskService.deleteTask(id).enqueue(callBack);
    }

    public void updateTask(int id, UpdateTaskRequest request, Callback<TaskResponse> callBack) {
        taskService.updateTask(id, request).enqueue(callBack);
    }

    public void uploadConfirmationImage(byte[] imageBytes, String fileName, Callback<TaskResponse> callback) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestFile);
        taskService.uploadConfirmation(body).enqueue(callback);
    }
//    public void uploadConfirmationImage(MultipartBody.Part image, String fileName, Callback<TaskResponse> callback) {
//        taskService.uploadConfirmation(image).enqueue(callback);
//    }



}
