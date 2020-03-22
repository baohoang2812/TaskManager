package baohg.taskmanager.baohg.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.R;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    TextView txtName, txtDescription, txtCreatedTime, txtGroupId;
    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtGroupName);
        txtCreatedTime = itemView.findViewById(R.id.txtCreatedTime);
        txtDescription = itemView.findViewById(R.id.txtDescription);
        txtGroupId = itemView.findViewById(R.id.txtGroupId);
    }

    public TextView getTxtName() {
        return txtName;
    }

    public TextView getTxtDescription() {
        return txtDescription;
    }

    public TextView getTxtCreatedTime() {
        return txtCreatedTime;
    }

    public TextView getTxtGroupId() {
        return txtGroupId;
    }
}
