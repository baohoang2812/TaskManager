package baohg.taskmanager;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.daos.TaskDAO;
import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.request.CreateTaskRequest;
import baohg.taskmanager.baohg.responses.TaskResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskCreationFragment extends Fragment {
    DateRangePickerFragment dateRangePickerFragment;
    EditText edtName,edtHandler, edtDescription, edtSourceId, edtStartTime, edtEndTime;
    CreateTaskRequest createTaskRequest;

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
        int userId = sharedPreferences.getInt("userId", 0);
        final String userFullName = sharedPreferences.getString("userFullName", "");
        edtName = view.findViewById(R.id.edtName);
        edtDescription = view.findViewById(R.id.edtDescription);
        edtSourceId = view.findViewById(R.id.edtSource);
        edtStartTime = view.findViewById(R.id.edtStartTime);
        edtEndTime = view.findViewById(R.id.edtEndTime);
        edtHandler = view.findViewById(R.id.txtHandlerId);
        edtHandler.setText(userId + "");
        edtSourceId.setVisibility(View.GONE);
        edtHandler.setEnabled(false);
        createTaskRequest = new CreateTaskRequest();
        // create task from failed task
        Bundle bundle = getArguments();
        if (bundle != null) {
            TaskDTO source = (TaskDTO) bundle.getSerializable("source");
            edtName.setText(source.getName());
            edtDescription.setText(source.getDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            edtStartTime.setText(sdf.format(source.getStartTime()));
            edtEndTime.setText(sdf.format(source.getEndTime()));
            edtSourceId.setText(source.getTaskId()+"");
            edtSourceId.setVisibility(View.GONE);
            edtSourceId.setVisibility(View.VISIBLE);
            edtHandler.setEnabled(true);
            edtHandler.setVisibility(View.VISIBLE);
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
                if(!txtStartDate.isEmpty() && !txtEndDate.isEmpty()){
                    try{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = sdf.parse(txtStartDate);
                        Date endDate = sdf.parse(txtEndDate);
                        if(startDate.after(endDate)){
                            isValid = false;
                            errorMsg += "Start Date must before End Date \n";
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if (isValid) {
                    TaskDAO taskDAO = new TaskDAO();
                    createTaskRequest.setName(taskName);
                    createTaskRequest.setDescription(description);
                    String txtSourceId = edtSourceId.getText().toString();
                    Integer sourceId = txtSourceId.isEmpty() ? null : Integer.parseInt(txtSourceId);
                    String txtHandlerId = edtHandler.getText().toString();
                    Integer handlerId = txtHandlerId.isEmpty() || txtHandlerId == null ? null : Integer.parseInt(txtHandlerId);
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

}
