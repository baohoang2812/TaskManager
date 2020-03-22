package baohg.taskmanager.baohg.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.MainActivity;
import baohg.taskmanager.R;
import baohg.taskmanager.UserDetailFragment;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.view_holders.UserViewHolder;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private List<UserDTO> userList;

    public UserAdapter(List<UserDTO> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_card, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(userView);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        holder.getTxtFullName().setText(userList.get(position).getFullName());
        holder.getTxtRole().setText(userList.get(position).getRoleName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetailFragment userDetailFragment = new UserDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userList.get(position).getUserId());
                userDetailFragment.setArguments(bundle);
                MainActivity mainActivity = (MainActivity) v.getContext();
                mainActivity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragmentContainer, userDetailFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}
