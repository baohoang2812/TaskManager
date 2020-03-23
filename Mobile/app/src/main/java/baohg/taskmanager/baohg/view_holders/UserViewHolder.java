package baohg.taskmanager.baohg.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    TextView txtFullName, txtRole, txtUsername;
    View userView;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        txtFullName = itemView.findViewById(R.id.txtFullName);
        txtRole = itemView.findViewById(R.id.txtRole);
        txtUsername = itemView.findViewById(R.id.txtUsername);
        userView = itemView;
    }

    public TextView getTxtFullName() {
        return txtFullName;
    }

    public TextView getTxtRole() {
        return txtRole;
    }

    public TextView getTxtUsername() {
        return txtUsername;
    }
}
