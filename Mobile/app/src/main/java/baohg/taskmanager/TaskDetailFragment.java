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

import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.daos.TaskDAO;
import baohg.taskmanager.baohg.dtos.TaskDTO;
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
    Button btnDeleteTask;
    TaskDAO taskDAO;
    int taskId;

    public TaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        try {
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
            showTaskDetail();
            deleteTask();
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
                            txtCreatedTime.setText(taskDTO.getCreatedTime().toString());
                            txtHandler.setText(Integer.toString(taskDTO.getHandlerId()));
                            txtCreator.setText(taskDTO.getCreator());
                            txtStatus.setText(Integer.toString(taskDTO.getStatusId()));
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
}
