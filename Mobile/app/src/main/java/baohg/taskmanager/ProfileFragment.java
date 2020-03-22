package baohg.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.responses.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    TextView txtFullname, txtEmail, txtRole, txtPhone;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        txtFullname = view.findViewById(R.id.txtFullName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtRole = view.findViewById(R.id.txtRole);
        txtPhone = view.findViewById(R.id.txtPhone);
        Button btnLogOut = view.findViewById(R.id.btnLogOut);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("baohg.taskmanager_preferences", Context.MODE_PRIVATE);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int userId = bundle.getInt("userId", 0);
            loadUserProfile(userId);
        } else {
            loadUserProfile(sharedPreferences.getInt("userId", 0));
        }
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("baohg.taskmanager_preferences", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear();
                startActivity(intent);
            }
        });
        return view;
    }

    private void loadUserProfile(int userId) {
        UserDAO userDAO = new UserDAO();

        userDAO.getUserProfile(userId, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == ResponseCodeConstant.OK) {
                        UserDTO userDTO = response.body().getData();
                        txtFullname.setText(userDTO.getFullName());
                        txtEmail.setText(userDTO.getEmail());
                        txtRole.setText(userDTO.getRoleName());
                        txtPhone.setText(userDTO.getPhoneNumber());
                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "FAILURE", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
