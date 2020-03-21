package baohg.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.baohg.adapters.UserAdapter;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.responses.GetUserResponse;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class GroupFragment extends Fragment {
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    Button btnSearch;
    List<UserDTO> userList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_group, container, false);
        recyclerView = view.findViewById(R.id.wgRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadAllUserInGroup(getActivity().getSharedPreferences("baohg.taskmanager_preferences", MODE_PRIVATE).getInt("groupId",0));
        btnSearch = view.findViewById(R.id.btnSearchByQRCode);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    public void loadAllUserInGroup(int groupId){
        UserDAO userDAO = new UserDAO();
        userDAO.getAllUser(groupId, new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if(response.isSuccessful()){
                    if(response.code() == ResponseCodeConstant.OK){
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
