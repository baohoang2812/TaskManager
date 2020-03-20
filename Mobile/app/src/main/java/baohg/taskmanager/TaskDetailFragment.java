package baohg.taskmanager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.constants.RoleConstant;
import baohg.taskmanager.baohg.constants.StatusConstant;
import baohg.taskmanager.baohg.daos.StatusDAO;
import baohg.taskmanager.baohg.daos.TaskDAO;
import baohg.taskmanager.baohg.dtos.StatusDTO;
import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.request.UpdateTaskRequest;
import baohg.taskmanager.baohg.responses.StatusResponse;
import baohg.taskmanager.baohg.responses.TaskResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {
    EditText edtTaskName, edtDescription, edtReport;
    TextView txtStatus, txtCreatedTime, txtHandler, txtCreator;
    Button btnDeleteTask, btnSave, btnUpload;
    TaskDAO taskDAO;
    int taskId;
    int statusId;
    DateRangePickerFragment dateRangePickerFragment;
    StatusDTO statusDTO;
    private static int RESULT_UPLOAD_IMAGE = 1;
    ImageView imageView;

    public TaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        taskDAO = new TaskDAO();
        Bundle bundle = getArguments();
        taskId = bundle.getInt("taskId", 0);
        edtTaskName = view.findViewById(R.id.edtTaskName);
        edtDescription = view.findViewById(R.id.edtDescription);
        edtReport = view.findViewById(R.id.edtReport);
        txtCreatedTime = view.findViewById(R.id.txtCreatedTime);
        txtHandler = view.findViewById(R.id.txtHandler);
        txtCreator = view.findViewById(R.id.txtCreator);
        txtStatus = view.findViewById(R.id.txtStatus);
        btnDeleteTask = view.findViewById(R.id.btnDeleteTask);
        btnSave = view.findViewById(R.id.btnSave);
        btnUpload = view.findViewById(R.id.btnUpload);
        imageView = view.findViewById(R.id.imgView);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("baohg.taskmanager_preferences", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("userRole", "");
        if (RoleConstant.ADMIN.equalsIgnoreCase(userRole) || RoleConstant.MANAGER.equalsIgnoreCase(userRole)) {
            TaskReviewFragment taskReviewFragment = new TaskReviewFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.fragmentReviewSection, taskReviewFragment).commit();
//            taskReviewFragment.getBtnAccept().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    changeStatus(StatusConstant.ACCEPTED);
//                }
//            });
//            taskReviewFragment.getBtnDecline().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    changeStatus(StatusConstant.REJECTED);
//                }
//            });
//            taskReviewFragment.getBtnFinish().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    changeStatus(StatusConstant.FINISHED);
//                }
//            });
        }
        try {
            showTaskDetail();
            deleteTask();
            saveChanges();
            uploadConfirmationImage();
        } catch (Exception e) {
            Log.d("Task Detail Exception", e.getMessage());
        }
        return view;
    }

    public void deleteTask() {
        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDAO.deleteTask(taskId, new Callback<TaskResponse>() {
                    @Override
                    public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                        if (response.isSuccessful()) {
                            TaskResponse taskResponse = response.body();
                            switch (response.code()) {
                                case ResponseCodeConstant.NOT_FOUND: {
                                    Toast.makeText(getActivity(), "Task Not Found", Toast.LENGTH_SHORT).show();
                                }
                                case ResponseCodeConstant.ERROR: {
                                    Toast.makeText(getActivity(), "ERROR: " + taskResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("ERROR", taskResponse.getMessage());
                                }
                                case ResponseCodeConstant.NO_CONTENT: {
                                    getActivity()
                                            .getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragmentContainer, new TaskFragment()).commit();
                                }
                                default:
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskResponse> call, Throwable t) {
                        Log.d("FAILURE", t.getMessage());
                    }
                });
            }
        });
    }

    public void showTaskDetail() {

        taskDAO.getTaskDetail(taskId, new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful()) {
                    TaskResponse responseBody = response.body();
                    switch (response.code()) {
                        case ResponseCodeConstant.NOT_FOUND: {
                            Toast.makeText(getActivity(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case ResponseCodeConstant.OK: {
                            TaskDTO taskDTO = responseBody.getData();
                            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
                            edtTaskName.setText(taskDTO.getName());
                            edtDescription.setText(taskDTO.getDescription());
                            edtReport.setText(taskDTO.getReport());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String createdTime = sdf.format(taskDTO.getCreatedTime());
                            txtCreatedTime.setText(createdTime);
                            txtHandler.setText(Integer.toString(taskDTO.getHandlerId()));
                            txtCreator.setText(taskDTO.getCreator());
                            statusId = taskDTO.getStatusId();
                            txtStatus.setText(taskDTO.getStatusName());
                            dateRangePickerFragment = new DateRangePickerFragment();
                            Bundle bundle = new Bundle();
                            Calendar startTime = Calendar.getInstance();
                            if (taskDTO.getStartTime() != null) {
                                startTime.setTime(taskDTO.getStartTime());
                            }
                            Calendar endTime = Calendar.getInstance();
                            if (taskDTO.getEndTime() != null) {
                                endTime.setTime(taskDTO.getEndTime());
                            }
                            bundle.putSerializable("startTime", startTime);
                            bundle.putSerializable("endTime", endTime);
                            dateRangePickerFragment.setArguments(bundle);
                            getChildFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.dateRangePickerFragment, dateRangePickerFragment)
                                    .commit();
                            break;
                        }
                        default: {
                            Toast.makeText(getActivity(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "FAILURE", Toast.LENGTH_SHORT).show();
                Log.d("FAILURE", t.getMessage());
            }
        });

    }

    public void saveChanges() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateTaskRequest request = new UpdateTaskRequest();
                String name = edtTaskName.getText().toString();
                String description = edtDescription.getText().toString();
                String report = edtReport.getText().toString();
                request.setName(name);
                request.setDescription(description);
                String startTime = dateRangePickerFragment.getEdtStartTime().getText().toString();
                String endTime = dateRangePickerFragment.getEdtEndTime().getText().toString();
                request.setReport(report);
                request.setStartTime(startTime);
                request.setCreatedTime(txtCreatedTime.getText().toString());
                request.setCreator(txtCreator.getText().toString());
                request.setStatusId(statusId);
                request.setEndTime(endTime);
                taskDAO.updateTask(taskId, request, new Callback<TaskResponse>() {
                    @Override
                    public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "Update success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Update On Failure", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public void uploadConfirmationImage() {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_UPLOAD_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_UPLOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                imageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void changeStatus(String name){
        StatusDAO statusDAO = new StatusDAO();
        statusDAO.getAllStatus(name, new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.isSuccessful() && response.body().getStatusList().size() == 1){
                    statusDTO = response.body().getStatusList().get(0);
                    if(statusDTO != null){
                        txtStatus.setText(statusDTO.getName());
                        statusId = statusDTO.getStatusId();
                    }else{
                        Toast.makeText(getContext(), "Status not exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
