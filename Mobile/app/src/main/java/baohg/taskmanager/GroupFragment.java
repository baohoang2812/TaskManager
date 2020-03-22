package baohg.taskmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.baohg.adapters.GroupAdapter;
import baohg.taskmanager.baohg.adapters.UserAdapter;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.constants.RoleConstant;
import baohg.taskmanager.baohg.daos.GroupDAO;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.dtos.GroupDTO;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.request.GetUserRequest;
import baohg.taskmanager.baohg.responses.GetGroupResponse;
import baohg.taskmanager.baohg.responses.GetUserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class GroupFragment extends Fragment {
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    GroupAdapter groupAdapter;
    Button btnSearch, btnCreateGroup;
    List<UserDTO> userList;
    private IntentIntegrator qrScanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        recyclerView = view.findViewById(R.id.wgRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("baohg.taskmanager_preferences", MODE_PRIVATE);
        String userRole = sharedPreferences.getString("userRole", "");
        btnSearch = view.findViewById(R.id.btnSearchByQRCode);
        btnCreateGroup = view.findViewById(R.id.btnCreateGroup);
        qrScanner = new IntentIntegrator(getActivity());
        qrScanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        if (!RoleConstant.ADMIN.equalsIgnoreCase(userRole)) {
            loadAllUserInGroup(sharedPreferences.getInt("groupId", 0));
            btnCreateGroup.setVisibility(View.GONE);
            if (RoleConstant.USER.equalsIgnoreCase(userRole)) {
                btnSearch.setVisibility(View.GONE);
            }
        } else {
            loadAllGroup();
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScanner.initiateScan();
            }
        });
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupCreationFragment creationFragment = new GroupCreationFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, creationFragment).commit();
            }
        });
        return view;
    }


    public void loadAllUserInGroup(int groupId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }

    private void loadAllGroup() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        GroupDAO groupDAO = new GroupDAO();
        groupDAO.getAllGroup(new Callback<GetGroupResponse>() {
            @Override
            public void onResponse(Call<GetGroupResponse> call, Response<GetGroupResponse> response) {
                if (response.isSuccessful()) {
                    List<GroupDTO> groupList = response.body().getData();
                    groupAdapter = new GroupAdapter(groupList);
                    recyclerView.setAdapter(groupAdapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetGroupResponse> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }
}
