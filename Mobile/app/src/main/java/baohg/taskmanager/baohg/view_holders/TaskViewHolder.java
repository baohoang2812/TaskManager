package baohg.taskmanager.baohg.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTaskName;
    public TextView txtDescription;
    public TextView txtEndTime;
    public View cardView;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView;
        txtTaskName = itemView.findViewById(R.id.txtTaskName);
        txtDescription = itemView.findViewById(R.id.txtDescription);
        txtEndTime = itemView.findViewById(R.id.txtEndTime);
    }
    public TextView getTxtTaskName() {
        return txtTaskName;
    }

    public TextView getTxtDescription() {
        return txtDescription;
    }

    public TextView getTxtEndTime() {
        return txtEndTime;
    }
}
