package baohg.taskmanager;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.baohg.adapters.TaskAdapter;
import baohg.taskmanager.baohg.daos.TaskDAO;
import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.request.GetTaskRequest;
import baohg.taskmanager.baohg.responses.GetTaskResponse;
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

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        //get recycle view from xml
        recyclerView = view.findViewById(R.id.wgRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadAllTask();
        // Inflate the layout for this fragment
        Button button = view.findViewById(R.id.btnAddTask);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new TaskCreationFragment());
                fragmentTransaction.commit();
            }
        });


        return view;
    }



    public void loadAllTask(){
        try{
            TaskDAO taskDAO = new TaskDAO();
            GetTaskRequest request = new GetTaskRequest();
            request.setUserId(17);

            taskDAO.getAllTask(request, new Callback<GetTaskResponse>() {
                @Override
                public void onResponse(Call<GetTaskResponse> call, Response<GetTaskResponse> response) {
                    switch (response.code()){
                        case 200:
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            taskList = response.body().getData();
                            taskAdapter = new TaskAdapter(taskList, getActivity());
                            recyclerView.setAdapter(taskAdapter);
                            break;
                        case 401:
                            Toast.makeText(getActivity(), "Unauthorized", Toast.LENGTH_SHORT).show();
                        case 500:
                            Toast.makeText(getActivity(), "Error:" , Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "Default", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Log.d("Get All Task Response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                }

                @Override
                public void onFailure(Call<GetTaskResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed" + t.getMessage() , Toast.LENGTH_LONG).show();
                    Log.d("Failure", t.getMessage());
                }
            });
        }catch(Exception e){
            Log.d("Task Fragment Exception", e.getMessage());
        }
    }
}
