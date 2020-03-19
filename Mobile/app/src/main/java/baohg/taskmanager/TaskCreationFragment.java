package baohg.taskmanager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.daos.TaskDAO;
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
    EditText edtName, edtDescription, edtSourceId, edtHandlerId, edtStartTime, edtEndTime;
    CreateTaskRequest createTaskRequest;

    public TaskCreationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_creation, container, false);
        edtName = view.findViewById(R.id.edtName);
        edtDescription = view.findViewById(R.id.edtDescription);
        edtSourceId = view.findViewById(R.id.edtSource);
        edtHandlerId = view.findViewById(R.id.edtHandler);
        edtStartTime = view.findViewById(R.id.edtStartTime);
        edtEndTime = view.findViewById(R.id.edtEndTime);
        createTaskRequest = new CreateTaskRequest();
        dateRangePickerFragment = (DateRangePickerFragment) getChildFragmentManager().findFragmentById(R.id.dateRangePickerFragment);
        Button btnCreate = view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDAO taskDAO = new TaskDAO();
                createTaskRequest.setName(edtName.getText().toString());
                createTaskRequest.setDescription(edtDescription.getText().toString());
                String txtSourceId = edtSourceId.getText().toString();
                String txtHandlerId = edtHandlerId.getText().toString();
                Integer sourceId = txtSourceId.isEmpty() ? null : Integer.parseInt(txtSourceId);
                Integer handlerId = txtHandlerId.isEmpty() ? null : Integer.parseInt(txtHandlerId);
                createTaskRequest.setSourceId(sourceId);
                createTaskRequest.setHandlerId(handlerId);
                createTaskRequest.setStartTime(dateRangePickerFragment.getEdtStartTime().getText().toString());
                createTaskRequest.setEndTime(dateRangePickerFragment.getEdtEndTime().getText().toString());
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
                Toast.makeText(getActivity(), "Create Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
