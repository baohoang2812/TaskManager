package baohg.taskmanager.baohg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.R;
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
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.getTxtTaskName().setText(taskList.get(position).getName());
        holder.getTxtDescription().setText(taskList.get(position).getDescription());
        Date endTime = taskList.get(position).getEndTime();
        holder.getTxtEndTime().setText(endTime != null ? endTime.toString() : null);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

}


