package baohg.taskmanager;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.RoleConstant;
import baohg.taskmanager.baohg.daos.TaskDAO;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.request.CreateTaskRequest;
import baohg.taskmanager.baohg.request.GetUserRequest;
import baohg.taskmanager.baohg.responses.GetUserResponse;
import baohg.taskmanager.baohg.responses.TaskResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskCreationFragment extends Fragment {
    DateRangePickerFragment dateRangePickerFragment;
    EditText edtName, edtDescription, edtSourceId, edtStartTime, edtEndTime;
    CreateTaskRequest createTaskRequest;
    Spinner spHandler;
    Integer handlerId;
    int userId;

    public TaskCreationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_creation, container, false);
        // get User info
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("baohg.taskmanager_preferences", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);
        final String userFullName = sharedPreferences.getString("userFullName", "");
        String roleName = sharedPreferences.getString("userRole", "");
        int groupId = sharedPreferences.getInt("groupId", 0);
        edtName = view.findViewById(R.id.edtName);
        edtDescription = view.findViewById(R.id.edtDescription);
        edtSourceId = view.findViewById(R.id.edtSource);
        edtStartTime = view.findViewById(R.id.edtStartTime);
        edtEndTime = view.findViewById(R.id.edtEndTime);
        edtSourceId.setVisibility(View.GONE);
        createTaskRequest = new CreateTaskRequest();
        spHandler = view.findViewById(R.id.spHandler);


        switch (roleName) {
            case RoleConstant.ADMIN: {
                loadHandler(null);
                break;
            }
            case RoleConstant.MANAGER: {
                loadHandler(groupId);
                break;
            }
            default: {
                spHandler.setVisibility(View.GONE);
                handlerId = userId;
            }
        }
        // create task from failed task
        Bundle bundle = getArguments();
        if (bundle != null) {
            TaskDTO source = (TaskDTO) bundle.getSerializable("source");
            edtName.setText(source.getName());
            edtDescription.setText(source.getDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            edtStartTime.setText(sdf.format(source.getStartTime()));
            edtEndTime.setText(sdf.format(source.getEndTime()));
            edtSourceId.setText(source.getTaskId() + "");
            edtSourceId.setVisibility(View.GONE);
            edtSourceId.setVisibility(View.VISIBLE);
        }
        dateRangePickerFragment = (DateRangePickerFragment) getChildFragmentManager().findFragmentById(R.id.dateRangePickerFragment);
        Button btnCreate = view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                String errorMsg = "";
                String taskName = edtName.getText().toString();
                String description = edtDescription.getText().toString();
                if (isEmpty(taskName)) {
                    isValid = false;
                    errorMsg += "Name is required \n";
                }
                if (isEmpty(description)) {
                    isValid = false;
                    errorMsg += "Description is required \n";
                }
                String txtStartDate = dateRangePickerFragment.getEdtStartTime().getText().toString();
                String txtEndDate = dateRangePickerFragment.getEdtEndTime().getText().toString();
                if (!txtStartDate.isEmpty() && !txtEndDate.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = sdf.parse(txtStartDate);
                        Date endDate = sdf.parse(txtEndDate);
                        if (startDate.after(endDate)) {
                            isValid = false;
                            errorMsg += "Start Date must before End Date \n";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (isValid) {
                    TaskDAO taskDAO = new TaskDAO();
                    createTaskRequest.setName(taskName);
                    createTaskRequest.setDescription(description);
                    String txtSourceId = edtSourceId.getText().toString();
                    Integer sourceId = txtSourceId.isEmpty() ? null : Integer.parseInt(txtSourceId);
                    createTaskRequest.setSourceId(sourceId);
                    createTaskRequest.setHandlerId(handlerId);
                    createTaskRequest.setStartTime(dateRangePickerFragment.getEdtStartTime().getText().toString());
                    createTaskRequest.setEndTime(dateRangePickerFragment.getEdtEndTime().getText().toString());
                    createTaskRequest.setCreator(userFullName);
                    taskDAO.createTask(createTaskRequest, new Callback<TaskResponse>() {
                        @Override
                        public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Create Task Successfully", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragmentContainer, new TaskFragment())
                                        .commit();
                            } else {
                                Toast.makeText(getActivity(), "Response fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TaskResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Invalid");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("Got It", null);
                    builder.show();
                }
            }
        });
        return view;
    }

    private boolean isEmpty(String text) {
        return text.isEmpty();
    }

    private void loadHandler(Integer groupId) {
        UserDAO userDAO = new UserDAO();
        GetUserRequest request = new GetUserRequest();
        request.setGroupId(groupId);
        userDAO.getAllUser(request, new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<UserDTO> userList = response.body().getData();
                        ArrayAdapter<UserDTO> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, userList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spHandler.setAdapter(adapter);

                        spHandler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                UserDTO userDTO = (UserDTO) parent.getItemAtPosition(position);
                                handlerId = userDTO.getUserId() != 0 ? userDTO.getUserId() : null;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        if (userList != null) {
                            for (int i = 0; i < adapter.getCount(); i++) {
                                if (adapter.getItem(i).getUserId() == userId) {
                                    spHandler.setSelection(i);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {

            }
        });
    }
}
