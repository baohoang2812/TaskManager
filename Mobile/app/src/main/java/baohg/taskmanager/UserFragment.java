package baohg.taskmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import baohg.taskmanager.baohg.adapters.UserAdapter;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.request.GetUserRequest;
import baohg.taskmanager.baohg.responses.GetUserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {
    UserAdapter userAdapter;
    RecyclerView recyclerView;
    Button btnCreate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container ,false);
        recyclerView = view.findViewById(R.id.wgRecyclerView);
        btnCreate = view.findViewById(R.id.btnCreateUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadAllUser();
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragmentContainer, new UserCreateFragment()).commit();
            }
        });
        return view;
    }

    private void loadAllUser(){
        UserDAO userDAO = new UserDAO();
        GetUserRequest request = new GetUserRequest();
        userDAO.getAllUser(request, new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if(response.isSuccessful()){
                    userAdapter = new UserAdapter(response.body().getData());
                    recyclerView.setAdapter(userAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
