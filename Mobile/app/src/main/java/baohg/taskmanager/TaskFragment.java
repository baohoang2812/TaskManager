package baohg.taskmanager;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.baohg.adapters.TaskAdapter;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.constants.RoleConstant;
import baohg.taskmanager.baohg.daos.StatusDAO;
import baohg.taskmanager.baohg.daos.TaskDAO;
import baohg.taskmanager.baohg.dtos.StatusDTO;
import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.request.GetTaskRequest;
import baohg.taskmanager.baohg.responses.GetTaskResponse;
import baohg.taskmanager.baohg.responses.StatusResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {
    private List<TaskDTO> taskList;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    DateRangePickerFragment dateRangePickerFragment;
    Button btnAddTask, btnFilter;
    Spinner spStatus;
    List<StatusDTO> statusSource;
    View taskView;
    GetTaskRequest getTaskRequest;
    int userId;
    AlertDialog.Builder dialog;
    AlertDialog alertDialog;
    String userRole;
    EditText edtHandlerId;

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        taskView = inflater.inflate(R.layout.dialog_filter, container, false);
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("baohg.taskmanager_preferences", Context.MODE_PRIVATE);
        userId = sharedPreference.getInt("userId", 0);
        userRole = sharedPreference.getString("userRole", null);
        recyclerView = view.findViewById(R.id.wgRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadAllTask();
        btnAddTask = view.findViewById(R.id.btnAddTask);
        btnFilter = view.findViewById(R.id.btnFilter);
        buildAlertDialog();
        addTaskListener();
        addFilterTaskListener();
        return view;
    }

    private void buildAlertDialog() {
        dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(taskView);
        dialog.setCancelable(true);
        edtHandlerId = taskView.findViewById(R.id.edtHandlerId);
        if (userRole != null && (RoleConstant.ADMIN.equalsIgnoreCase(userRole) || RoleConstant.MANAGER.equalsIgnoreCase(userRole))) {
            edtHandlerId.setEnabled(true);
        } else {
            edtHandlerId.setVisibility(View.GONE);
        }
        dialog.setTitle("Task Filter");
        dialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TaskDAO taskDAO = new TaskDAO();
                dateRangePickerFragment = (DateRangePickerFragment) getChildFragmentManager().findFragmentById(R.id.dateRangePickerFragment2);
                getTaskRequest.setStartTime(dateRangePickerFragment.getEdtStartTime().getText().toString());
                getTaskRequest.setEndTime(dateRangePickerFragment.getEdtEndTime().getText().toString());
                String txtHandlerId = edtHandlerId.getText().toString();
                Integer handlerId = txtHandlerId.isEmpty() ? null : Integer.parseInt(txtHandlerId);
                getTaskRequest.setHandlerId(handlerId);
                taskDAO.getAllTask(getTaskRequest, new Callback<GetTaskResponse>() {
                    @Override
                    public void onResponse(Call<GetTaskResponse> call, Response<GetTaskResponse> response) {
                        if (response.isSuccessful()) {
                            taskList = response.body().getData();
                            taskAdapter = new TaskAdapter(taskList);
                            recyclerView.setAdapter(taskAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTaskResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Failure get Task By Filter", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
                dialog.dismiss();

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = dialog.create();
    }

    public void loadAllTask() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            TaskDAO taskDAO = new TaskDAO();
            GetTaskRequest request = new GetTaskRequest();
            request.setUserId(userId);
            if (RoleConstant.USER.equalsIgnoreCase(userRole)) {
                request.setHandlerId(userId);
            }
            taskDAO.getAllTask(request, new Callback<GetTaskResponse>() {
                @Override
                public void onResponse(Call<GetTaskResponse> call, Response<GetTaskResponse> response) {
                    switch (response.code()) {
                        case ResponseCodeConstant.OK:
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            taskList = response.body().getData();
                            taskAdapter = new TaskAdapter(taskList);
                            recyclerView.setAdapter(taskAdapter);
                            break;
                        case ResponseCodeConstant.UNAUTHORIZED:
                            Toast.makeText(getActivity(), "Unauthorized", Toast.LENGTH_SHORT).show();
                        case ResponseCodeConstant.ERROR:
                            Toast.makeText(getActivity(), "Error:", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "Default", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetTaskResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Failure", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            Log.d("Task Fragment Exception", e.getMessage());
        }
    }

    public void addTaskListener() {
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new TaskCreationFragment())
                        .addToBackStack("tasks")
                        .commit();
            }
        });
    }

    public void addFilterTaskListener() {
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTaskRequest = new GetTaskRequest();
                getTaskRequest.setUserId(userId);
                statusSource = new ArrayList<>();
                StatusDAO statusDAO = new StatusDAO();
                statusDAO.getAllStatus(null, new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        if (response.isSuccessful()) {
                            statusSource = response.body().getStatusList();
                            statusSource.add(0, new StatusDTO(0, "Selected Status..."));
                            ArrayAdapter<StatusDTO> statusAdapter = new ArrayAdapter<>(
                                    getActivity(), android.R.layout.simple_spinner_item, statusSource);
                            statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spStatus = taskView.findViewById(R.id.spStatus);
                            spStatus.setAdapter(statusAdapter);
                            spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    StatusDTO statusDTO = (StatusDTO) parent.getItemAtPosition(position);
                                    getTaskRequest.setStatusId(statusDTO.getStatusId());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Load Status Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Failure Load Status", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

                alertDialog.show();
            }
        });
    }


}
