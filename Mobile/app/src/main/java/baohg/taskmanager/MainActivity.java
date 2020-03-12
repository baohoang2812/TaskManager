package baohg.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.baohg.adapters.TaskAdapter;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.dtos.UserDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TaskDTO> taskList;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get recycle view from xml
        recyclerView = findViewById(R.id.wgRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<TaskDTO>();
        taskList.add(new TaskDTO("Today",new Date(),"now"));
        taskList.add(new TaskDTO("Tomorrow",new Date(),"now"));
        taskList.add(new TaskDTO("Yesterday",new Date(),"now"));

        taskAdapter = new TaskAdapter(taskList, this);
        recyclerView.setAdapter(taskAdapter);
    }


}
