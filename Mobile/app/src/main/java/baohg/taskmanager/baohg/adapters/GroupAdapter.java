package baohg.taskmanager.baohg.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.GroupDetailFragment;
import baohg.taskmanager.MainActivity;
import baohg.taskmanager.R;
import baohg.taskmanager.baohg.dtos.GroupDTO;
import baohg.taskmanager.baohg.view_holders.GroupViewHolder;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {
    List<GroupDTO> groupList;
    public GroupAdapter(List<GroupDTO> groupList) {
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View groupView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_group_card, parent, false);
        GroupViewHolder groupViewHolder = new GroupViewHolder(groupView);
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        final GroupDTO groupDTO = groupList.get(position);
        holder.getTxtName().setText(groupDTO.getName());
        holder.getTxtDescription().setText(groupDTO.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        holder.getTxtCreatedTime().setText(sdf.format(groupDTO.getCreatedTime()));
        holder.getTxtGroupId().setText(groupDTO.getGroupId()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDetailFragment groupDetailFragment = new GroupDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("groupId", groupDTO.getGroupId());
                groupDetailFragment.setArguments(bundle);
                MainActivity mainActivity = (MainActivity)v.getContext();
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, groupDetailFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
