package baohg.taskmanager.baohg.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.MainActivity;
import baohg.taskmanager.R;
import baohg.taskmanager.TaskDetailFragment;
import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.view_holders.TaskViewHolder;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<TaskDTO> taskList;
    Context context;

    public TaskAdapter(List<TaskDTO> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout file
        View taskView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task_card, parent ,false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(taskView);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, final int position) {
        holder.getTxtTaskName().setText(taskList.get(position).getName());
        holder.getTxtDescription().setText(taskList.get(position).getDescription());
        Date endTime = taskList.get(position).getEndTime();
        holder.getTxtEndTime().setText(endTime != null ? endTime.toString() : null);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetailFragment detailFragment = new TaskDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("taskId", taskList.get(position).taskId);
                detailFragment.setArguments(bundle);
                MainActivity activity =(MainActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, detailFragment).addToBackStack("tasks")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

}


