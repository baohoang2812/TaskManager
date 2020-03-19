package baohg.taskmanager;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.daos.TaskDAO;
import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.request.UpdateTaskRequest;
import baohg.taskmanager.baohg.responses.TaskResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {
    EditText edtTaskName, edtDescription, edtStartTime, edtEndTime, edtReport;
    TextView txtStatus, txtCreatedTime, txtHandler, txtCreator;
    Button btnDeleteTask, btnSave;
    TaskDAO taskDAO;
    int taskId;
    DateRangePickerFragment dateRangePickerFragment;

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
        try {
            showTaskDetail();
            deleteTask();
            saveChanges();
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
                            txtStatus.setText(Integer.toString(taskDTO.getStatusId()));
                            dateRangePickerFragment = new DateRangePickerFragment();
                            Bundle bundle = new Bundle();
                            Calendar startTime = Calendar.getInstance();
                            if(taskDTO.getStartTime() != null){
                                startTime.setTime(taskDTO.getStartTime());
                            }
                            Calendar endTime = Calendar.getInstance();
                            if(taskDTO.getEndTime() != null){
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

    public void saveChanges(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateTaskRequest request= new UpdateTaskRequest();
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
                request.setStatusId(txtStatus.getText().toString());
                request.setEndTime(endTime);
                taskDAO.updateTask(taskId, request, new Callback<TaskResponse>() {
                    @Override
                    public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getActivity(), "Update success", Toast.LENGTH_SHORT).show();
                        }else{
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
}
