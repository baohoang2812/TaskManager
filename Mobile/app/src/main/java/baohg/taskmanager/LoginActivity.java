package baohg.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.request.LoginRequest;
import baohg.taskmanager.baohg.responses.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

    public void clickToLogin(View view) {
        UserDAO userDAO = new UserDAO();
        LoginRequest request = new LoginRequest(edtUsername.getText().toString(), edtPassword.getText().toString());
        userDAO.checkLogin(request, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == ResponseCodeConstant.OK) {
                    SharedPreferences sharedPreferences = getSharedPreferences("baohg.taskmanager_preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    UserDTO userDTO = response.body().getData();
                    editor.putInt("userId", userDTO.getUserId());
                    editor.putString("userFullName", userDTO.getFullName());
                    editor.putString("userRole", userDTO.getRoleName());
                    editor.putInt("groupId", userDTO.getGroupId());
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }
}
