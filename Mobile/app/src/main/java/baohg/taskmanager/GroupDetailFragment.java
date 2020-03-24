package baohg.taskmanager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.baohg.adapters.UserAdapter;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.daos.GroupDAO;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.dtos.GroupDTO;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.request.GetUserRequest;
import baohg.taskmanager.baohg.responses.GetUserResponse;
import baohg.taskmanager.baohg.responses.GroupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDetailFragment extends Fragment {
    TextView txtName, txtDescription, txtCreatedTime;
    int groupId;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<UserDTO> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_detail, container, false);
        txtName = view.findViewById(R.id.txtGroupName);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtCreatedTime = view.findViewById(R.id.txtCreatedTime);
        recyclerView = view.findViewById(R.id.wgRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundle = getArguments();
        if (bundle != null) {
            groupId = bundle.getInt("groupId", 0);
            loadGroupDetail(groupId);
            loadAllUserInGroup(groupId);
        }
        return view;
    }

    private void loadGroupDetail(int groupId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        GroupDAO groupDAO = new GroupDAO();
        groupDAO.getGroupDetail(groupId, new Callback<GroupResponse>() {
            @Override
            public void onResponse(Call<GroupResponse> call, Response<GroupResponse> response) {
                if (response.isSuccessful()) {
                    GroupDTO groupDTO = response.body().getData();
                    txtName.setText(groupDTO.getName());
                    txtDescription.setText(groupDTO.getDescription());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    txtCreatedTime.setText(sdf.format(groupDTO.getCreatedTime()));
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GroupResponse> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }

    public void loadAllUserInGroup(int groupId) {
        UserDAO userDAO = new UserDAO();
        GetUserRequest request = new GetUserRequest();
        request.setGroupId(groupId);
        userDAO.getAllUser(request, new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == ResponseCodeConstant.OK) {
                        userList = response.body().getData();
                        userAdapter = new UserAdapter(userList);
                        recyclerView.setAdapter(userAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
